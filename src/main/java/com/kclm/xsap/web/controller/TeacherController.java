package com.kclm.xsap.web.controller;

import com.kclm.xsap.dto.TeacherDTO;
import com.kclm.xsap.entity.EmployeeEntity;
import com.kclm.xsap.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
    @ResponseBody
    public List<TeacherDTO> teacherList() {
        log.info("执行teacherList方法");
        List<EmployeeEntity> employeeEntities = employeeService.list();
        List<TeacherDTO> teacherDTOList = new ArrayList<>();
        for (EmployeeEntity employeeEntity : employeeEntities) {
            teacherDTOList.add(new TeacherDTO(employeeEntity));
        }
        return teacherDTOList;
    }
    @GetMapping("/x_teacher_list_data.do")
    public String toTeacherListData(@RequestParam("id") Long id,Model model) {
        log.info("进入x_teacher_list_data页面,id="+id);
        model.addAttribute("ID",id);
        return "employee/x_teacher_list_data";
    }

    @PostMapping("/teacherDetail.do")
    public TeacherDTO teacherDetail(@RequestParam("ID") Long id) {
        log.info("执行teacherDetail方法,ID="+id);
        return new TeacherDTO(employeeService.getById(id));
    }

}
