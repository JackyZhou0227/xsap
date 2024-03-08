package com.kclm.xsap.web.controller;

import com.kclm.xsap.model.dto.TeacherDTO;
import com.kclm.xsap.model.entity.EmployeeEntity;
import com.kclm.xsap.service.EmployeeService;
import com.kclm.xsap.utils.BeanError;
import com.kclm.xsap.utils.file.ImgManger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
    private final static Logger log = LoggerFactory.getLogger(TeacherController.class);

    private final static String TEACHER_IMG_DIR = "teacher_img";
    @Resource
    private EmployeeService employeeService;

    @GetMapping("/x_teacher_list.do")
    public String toTeacherList() {
        log.info("进入teacherList页面");
        return "employee/x_teacher_list";
    }

    @PostMapping("/teacherList.do")
    public ResponseEntity<Map<String, Object>> teacherList() {
        log.info("执行teacherList方法");
        List<EmployeeEntity> employeeEntities = employeeService.list();
        List<TeacherDTO> teacherDTOList = new ArrayList<>();
        Map<String, Object> returnData = new HashMap<>();
        for (EmployeeEntity employeeEntity : employeeEntities) {
            teacherDTOList.add(new TeacherDTO(employeeEntity));
        }
        returnData.put("data", teacherDTOList);
        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }

    @GetMapping("/x_teacher_add.do")
    public String toAddTeacher() {
        log.info("进入teacherAdd页面");
        return "employee/x_teacher_add";
    }

    @PostMapping("/teacherAdd.do")
    public ResponseEntity<Map<String, Object>> teacherAdd(@Valid EmployeeEntity employeeEntity, BindingResult bindingResult) {
        log.info("添加教师");
        Map<String, Object> returnData = new HashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("添加老师bean验证错误");
            Map<String, String> errorMap = BeanError.getErrorDataMap(bindingResult);
            returnData.put("errorMap", errorMap);
            returnData.put("code", 400);

            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }
        if (employeeService.isEmailExists(employeeEntity.getRoleEmail())) {
            returnData.put("msg", "该邮箱已存在");
            log.info("用户输入的邮箱已存在");
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }
        if (employeeService.isPhoneExists(employeeEntity.getPhone())) {
            returnData.put("msg", "该手机号已存在");
            log.info("用户输入的手机号已存在");
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }

        if (employeeService.save(employeeEntity)) {
            returnData.put("msg", "添加成功");
            returnData.put("code", 0);
            log.info("添加成功");
        } else {
            returnData.put("msg", "添加老师失败！请联系管理员。");
            log.info("添加失败");
        }

        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }

    @GetMapping("/x_teacher_update.do")
    public String toUpdateTeacher(@RequestParam("id") Long id, Model model) {
        log.info("进入teacherUpdate页面,id=" + id);
        TeacherDTO teacherInfo = new TeacherDTO(employeeService.getById(id));
        model.addAttribute("teacherMsg", teacherInfo);
        return "employee/x_teacher_update";
    }

    @PostMapping("/teacherEdit.do")
    public ResponseEntity<Map<String, Object>> teacherEdit(@Valid EmployeeEntity teacher, BindingResult bindingResult) {
        log.info("用户编辑教师信息，执行teacherEdit方法修改教师数据");

        Map<String, Object> returnData = new HashMap<>();
        Long id = teacher.getId();
        EmployeeEntity employeeEntity = employeeService.getById(id);

        if (bindingResult.hasErrors()) {
            log.info("教师数据bean验证错误");
            returnData.put("msg", BeanError.getErrorData(bindingResult));
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }
        if (employeeEntity == null) {
            returnData.put("msg", "该教师不存在");
            log.error("输入的教师id不存在");
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }
        if (employeeService.isEmailExists(teacher.getRoleEmail()) && !employeeEntity.getRoleEmail().equals(teacher.getRoleEmail())) {
            returnData.put("msg", "该邮箱已存在");
            log.info("用户输入的邮箱已存在");
            //model.addAttribute("CHECK_EMAIL_EXIST", true);
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }
        if (employeeService.isPhoneExists(teacher.getPhone()) && !employeeEntity.getPhone().equals(teacher.getPhone())) {
            returnData.put("msg", "该手机号已存在");
            log.info("用户输入的手机号已存在");
            //model.addAttribute("CHECK_PHONE_EXIST", true);
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }
        if (employeeService.updateById(teacher)) {
            returnData.put("msg", "修改成功");
            log.info("修改教师数据成功");
            returnData.put("code", 0);
        } else {
            returnData.put("msg", "修改失败");
            log.error("修改教师数据失败");
        }

        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }

    @GetMapping("/x_teacher_list_data.do")
    public String toTeacherListData(@RequestParam("id") Long id, Model model) {
        log.info("进入x_teacher_list_data页面,id=" + id);
        model.addAttribute("ID", id);
        return "employee/x_teacher_list_data";
    }

    @PostMapping("/teacherDetail.do")
    public ResponseEntity<Map<String, Object>> teacherDetail(@RequestParam("tid") Long id) {
        log.info("查看教师详细信息,tid=" + id);
        TeacherDTO teacherInfo = new TeacherDTO(employeeService.getById(id));
        Map<String, Object> returnData = new HashMap<>();
        returnData.put("data", teacherInfo);
        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }

    @PostMapping("/modifyUserImg.do")
    public ResponseEntity<Map<String, Object>> modifyUserImg(@RequestParam("id") Long id, @RequestParam("avatarFile") MultipartFile avatarUrl) {
        log.info("修改老师头像,id=" + id + ", 图片url=" + avatarUrl);
        ApplicationHome applicationHome = new ApplicationHome(getClass());
        Map<String, Object> returnData = new HashMap<>();

        if (id == null || employeeService.getById(id) == null) {
            log.error("用户id为空或用户不存在");
            returnData.put("msg", "用户id为空或用户不存在");
            return new ResponseEntity<>(returnData, HttpStatus.BAD_REQUEST);
        }
        if (avatarUrl.isEmpty()) {
            log.error("用户上传头像为空");
            returnData.put("msg", "用户头像为空");
            return new ResponseEntity<>(returnData, HttpStatus.BAD_REQUEST);
        }
        String filename = ImgManger.uploadImg(avatarUrl, applicationHome, TEACHER_IMG_DIR);
        if (filename == null) {
            log.error("用户上传头像失败");
            returnData.put("msg", "用户上传头像失败");
            return new ResponseEntity<>(returnData, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            log.info("用户上传头像成功");
            EmployeeEntity employeeEntity = employeeService.getById(id);
            employeeEntity.setAvatarUrl(filename);
            employeeEntity.setLastModifyTime(LocalDateTime.now());
            employeeService.updateById(employeeEntity);
            returnData.put("msg", "用户上传头像成功");
            returnData.put("userData1", employeeEntity);
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }
    }

}
