package com.kclm.xsap.web.controller;

import com.kclm.xsap.model.entity.GlobalReservationSetEntity;
import com.kclm.xsap.service.GlobalReservationSetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/globalSet")
public class GlobalSetController {

    private final static Logger log = LoggerFactory.getLogger(GlobalSetController.class);

    @Resource
    private GlobalReservationSetService globalReservationSetService;

    @GetMapping("/x_course_reservation.do")
    public String toCourseReservation(Model model) {
        log.info("前往course_reservation页面");
        GlobalReservationSetEntity set = globalReservationSetService.getById(1);
        model.addAttribute("GLOBAL_SET", set);
        return "course/x_course_reservation";
    }

    @PostMapping("/globalSetUpdate.do")
    public ResponseEntity<Map<String, Object>> globalSetUpdate(GlobalReservationSetEntity set) {
        log.info("更新全局设置");
        Map<String, Object> returnData = new HashMap<>();
        if (globalReservationSetService.updateById(set)) {
            returnData.put("code", 1);
            returnData.put("msg", "更新成功");
        } else {
            returnData.put("msg", "更新失败");
        }
        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }


}