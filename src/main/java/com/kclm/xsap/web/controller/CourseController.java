package com.kclm.xsap.web.controller;

import com.kclm.xsap.model.entity.CourseEntity;
import com.kclm.xsap.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/course")
public class CourseController{
    @Resource
    private CourseService courseService;
    private static final Logger log = LoggerFactory.getLogger(CourseController.class);

    @GetMapping("/x_course_list.do")
    public String toCourseList(){
        log.info("前往courseList页面");
        return "course/x_course_list";
    }

    @PostMapping("/courseList.do")
    public ResponseEntity<Map<String, Object>> courseList(){
        Map<String, Object> returnData = new HashMap<>();
        returnData.put("courseList", courseService.list());
        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }

    @GetMapping("/x_course_list_edit.do")
    public String toCourseListEdit(@RequestParam("id") Long id, Model model){
        log.info("前往courseListEdit页面");
        CourseEntity courseEntity = courseService.getById(id);
        model.addAttribute("courseInfo", courseEntity);
        return "course/x_course_list_edit";
    }

    @GetMapping("x_course_list_add.do")
    public String toCourseListAdd(){
        log.info("前往courseListAdd页面");
        return "course/x_course_list_add";
    }

    @PostMapping("/courseAdd.do")
    public ResponseEntity<Map<String, Object>> courseAdd(@Valid CourseEntity courseEntity, BindingResult bindingResult){
        return null;
    }
}
