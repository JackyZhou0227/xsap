package com.kclm.xsap.web.controller;

import com.kclm.xsap.model.dto.TeacherDTO;
import com.kclm.xsap.model.entity.EmployeeEntity;
import com.kclm.xsap.service.ClassRecordService;
import com.kclm.xsap.service.EmployeeService;
import com.kclm.xsap.service.ScheduleRecordService;
import com.kclm.xsap.utils.BeanError;
import com.kclm.xsap.utils.file.ImgManger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/teacher")
public class TeacherController {

    private final static String TEACHER_IMG_DIR = "teacher_img";
    @Resource
    private EmployeeService employeeService;

    @Resource
    private ClassRecordService classRecordService;

    @Resource
    private ScheduleRecordService scheduleRecordService;

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
            if (employeeEntity.getIsDeleted() == 0) {
                teacherDTOList.add(new TeacherDTO(employeeEntity));
            }
        }
        returnData.put("data", teacherDTOList);
        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }

    @GetMapping("/x_teacher_add.do")
    public String toAddTeacher() {
        log.info("进入teacherAdd页面");
        return "employee/x_teacher_add";
    }

    /**
     * 添加教师信息
     *
     * @param employeeEntity 包含教师信息的实体对象，必须是有效的。
     * @param bindingResult  用于存储数据绑定和验证结果的对象。
     * @return 返回一个响应实体，包含操作结果和相应的状态码。
     */
    @PostMapping("/teacherAdd.do")
    public ResponseEntity<Map<String, Object>> teacherAdd(@Valid EmployeeEntity employeeEntity, BindingResult bindingResult) {
        log.info("添加教师");
        Map<String, Object> returnData = new HashMap<>();
        // 验证输入实体是否有错误
        if (bindingResult.hasErrors()) {
            log.info("添加老师bean验证错误");
            // 如果有错误，将错误信息添加到返回数据中
            Map<String, String> errorMap = BeanError.getErrorDataMap(bindingResult);
            returnData.put("errorMap", errorMap);
            returnData.put("code", 400);

            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }
        // 检查邮箱是否已存在
        if (employeeService.isEmailExists(employeeEntity.getRoleEmail())) {
            returnData.put("msg", "该邮箱已存在");
            log.info("用户输入的邮箱已存在");
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }
        // 检查手机号是否已存在
        if (employeeService.isPhoneExists(employeeEntity.getPhone())) {
            returnData.put("msg", "该手机号已存在");
            log.info("用户输入的手机号已存在");
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }

        // 保存教师信息
        if (employeeService.save(employeeEntity)) {
            returnData.put("msg", "添加成功");
            returnData.put("code", 0);
            log.info("添加成功");
        } else {
            // 如果保存失败，返回相应的错误信息
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

    /**
     * 编辑教师信息
     *
     * @param teacher       一个经过验证的EmployeeEntity对象，包含待更新的教师信息
     * @param bindingResult 包含验证结果的对象，用于检查teacher对象中的数据错误
     * @return 返回一个ResponseEntity对象，其中包含操作结果信息（消息和状态码）
     */
    @PostMapping("/teacherEdit.do")
    public ResponseEntity<Map<String, Object>> teacherEdit(@Valid EmployeeEntity teacher, BindingResult bindingResult) {
        log.info("用户编辑教师信息，执行teacherEdit方法修改教师数据");

        Map<String, Object> returnData = new HashMap<>();
        Long id = teacher.getId();
        EmployeeEntity employeeEntity = employeeService.getById(id);

        // 验证传入的教师信息是否有错误
        if (bindingResult.hasErrors()) {
            log.info("教师数据bean验证错误");
            returnData.put("msg", BeanError.getErrorData(bindingResult));
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }
        // 检查数据库中是否存在该教师
        if (employeeEntity == null) {
            returnData.put("msg", "该教师不存在");
            log.error("输入的教师id不存在");
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }
        // 检查邮箱是否已被其他教师使用
        if (employeeService.isEmailExists(teacher.getRoleEmail()) && !employeeEntity.getRoleEmail().equals(teacher.getRoleEmail())) {
            returnData.put("msg", "该邮箱已存在");
            log.info("用户输入的邮箱已存在");
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }
        // 检查手机号是否已被其他教师使用
        if (employeeService.isPhoneExists(teacher.getPhone()) && !employeeEntity.getPhone().equals(teacher.getPhone())) {
            returnData.put("msg", "该手机号已存在");
            log.info("用户输入的手机号已存在");
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }
        // 更新教师信息
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

    /**
     * 删除老师
     * 1.删除成功。该老师已没有排课记录和计划）
     * 2.删除成功。该老师存在未完成的排课计划，但尚没有预约记录，
     * 2.1该老师没有已完成的排课记录，删除该老师信息后未完成的排课计划也会被删除
     * 2.2该老师存在已完成的排课记录，删除该老师信息后未完成的排课计划也会被删除，但已完成的排课记录会被保留
     * 3.删除失败。该老师存在未完成的排课记录且有预约，无法删除该老师
     * 4.删除成功。该老师没有未完成的排课计划，仅删除该老师信息后保留已有的排课记录
     *
     * @param id 老师id
     * @return ResponseEntity 携带删除结果和信息
     */
    @PostMapping("/deleteOne.do")
    public ResponseEntity<Map<String, Object>> deleteOneTeacher(@RequestParam("id") Long id) {
        log.debug("删除老师，id=" + id);
        Map<String, Object> returnData = new HashMap<>();
        if (employeeService.isAllowToDelete(id)) {
            log.info("该教师可被删除");
            EmployeeEntity employeeEntity = employeeService.getById(id);
            employeeEntity.setIsDeleted(1);
            log.info("isDeleted:" + employeeEntity.getIsDeleted());
            if (employeeService.removeById(employeeEntity)) {
                returnData.put("code", 0);
                returnData.put("msg", "该教师信息已成功删除！");
            } else {
                returnData.put("msg", "未知错误！请联系管理员！");
            }
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        } else {
            returnData.put("msg", "该教师存在未完成的排课记录且有预约，无法删除！");
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }
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

    /**
     * 修改用户头像
     *
     * @param id        用户ID，用于标识需要修改头像的用户
     * @param avatarUrl 用户上传的头像文件
     * @return ResponseEntity<Map < String, Object>> 包含操作结果信息的响应实体：
     * - 当操作成功时，返回状态码200（HttpStatus.OK）和包含成功消息及更新后的用户数据的Map；
     * - 当操作失败时，返回状态码400（HttpStatus.BAD_REQUEST）和包含错误消息的Map。
     */
    @PostMapping("/modifyUserImg.do")
    public ResponseEntity<Map<String, Object>> modifyUserImg(@RequestParam("id") Long id, @RequestParam("avatarFile") MultipartFile avatarUrl) {
        log.info("修改老师头像,id=" + id + ", 图片url=" + avatarUrl);
        ApplicationHome applicationHome = new ApplicationHome(getClass());
        Map<String, Object> returnData = new HashMap<>();

        // 检查用户ID是否为空或用户是否存在
        if (id == null || employeeService.getById(id) == null) {
            log.error("用户id为空或用户不存在");
            returnData.put("msg", "用户id为空或用户不存在");
            return new ResponseEntity<>(returnData, HttpStatus.BAD_REQUEST);
        }
        // 检查上传的头像文件是否为空
        if (avatarUrl.isEmpty()) {
            log.error("用户上传头像为空");
            returnData.put("msg", "用户头像为空");
            return new ResponseEntity<>(returnData, HttpStatus.BAD_REQUEST);
        }
        // 上传头像文件
        String filename = ImgManger.uploadImg(avatarUrl, applicationHome, TEACHER_IMG_DIR);
        if (filename == null) {
            log.error("用户上传头像失败");
            returnData.put("msg", "用户上传头像失败");
            return new ResponseEntity<>(returnData, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            log.info("用户上传头像成功");
            // 更新用户头像信息
            EmployeeEntity employeeEntity = employeeService.getById(id);
            employeeEntity.setAvatarUrl(filename);
            employeeEntity.setLastModifyTime(LocalDateTime.now());
            employeeService.updateById(employeeEntity);
            // 返回成功消息和更新后的用户数据
            returnData.put("msg", "用户上传头像成功");
            returnData.put("userData1", employeeEntity);
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }
    }

    @GetMapping("/toSearch.do")
    public ResponseEntity<Map<String, Object>> toSearchTeachers(@RequestParam("keyword") String keyword) {
        log.info("关键词搜索teacher,keyword=" + keyword);
        Map<String, Object> returnData = new HashMap<>();
        List<TeacherDTO> teacherDTOList = employeeService.getTeacherByKeyword(keyword);
        returnData.put("value", teacherDTOList);
        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }

    @PostMapping("/teacherClassRecord.do")
    public ResponseEntity<Map<String, Object>> teacherClassRecord(@RequestParam("tid") Long id) {
        log.info("查看教师上课记录,id=" + id);
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Map<String, Object> returnData = new HashMap<>();
        returnData.put("data", classRecordService.getTeacherClassRecordVoListByTeacherId(id));
        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }

}
