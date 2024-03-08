package com.kclm.xsap.web.controller;

import com.kclm.xsap.service.ScheduleRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    private final static Logger log = LoggerFactory.getLogger(ScheduleController.class);

    @Resource
    private ScheduleRecordService scheduleRecordService;

    @GetMapping("/x_course_schedule.do")
    public String toCourseSchedule() {
        return "course/x_course_schedule";
    }

    @PostMapping("/scheduleList.do")
    public ResponseEntity<Map<String, Object>> scheduleList() {
        //todo


        return null;
    }
}