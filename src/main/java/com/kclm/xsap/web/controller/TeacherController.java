package com.kclm.xsap.web.controller;

import com.kclm.xsap.dto.TeacherDTO;
import com.kclm.xsap.entity.EmployeeEntity;
import com.kclm.xsap.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
    private final static Logger log = LoggerFactory.getLogger(UserController.class);
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

    @PostMapping("/x_teacher_update.do")
    public String toUpdateTeacher(@RequestParam("id") Long id) {



        return "employee/x_teacher_update";
    }

    @GetMapping("/x_teacher_list_data.do")
    public String toTeacherListData(@RequestParam("id") Long id,Model model) {
        log.info("进入x_teacher_list_data页面,id="+id);
        model.addAttribute("ID",id);
        return "employee/x_teacher_list_data";
    }

    @PostMapping("/teacherDetail.do")
    public ResponseEntity<Map<String, Object>> teacherDetail(@RequestParam("tid") Long id) {
        log.info("查看教师详细信息,tid="+id);
        TeacherDTO teacherInfo = new TeacherDTO(employeeService.getById(id));
        Map<String, Object> returnData = new HashMap<>();
        returnData.put("data", teacherInfo);
        return new ResponseEntity<>(returnData,HttpStatus.OK);
    }


}
