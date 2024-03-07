package com.kclm.xsap.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {

    private final static Logger log = LoggerFactory.getLogger(StatisticsController.class);
    @GetMapping("/x_card_list_stat.do")
    public String toCardListStat() {
        log.info("前往card_list_stat页面");
        return "statistics/x_card_list_stat";
    }

    @GetMapping("/x_card_cost_stat.do")
    public String toCardCostStat() {
        log.info("前往card_cost_stat页面");
        return "statistics/x_card_cost_stat";
    }

    @GetMapping("/x_class_cost_stat.do")
    public String toClassCostStat() {
        log.info("前往class_cost_stat页面");
        return "statistics/x_class_cost_stat";
    }

    @GetMapping("/x_class_hour_stat.do")
    public String toClassHourStat() {
        log.info("前往class_hour_stat页面");
        return "statistics/x_class_hour_stat";
    }

    @GetMapping("/x_member_num_static.do")
    public String toMemberNumStatic() {
        log.info("前往member_num_static页面");
        return "statistics/x_member_num_static";
    }
}
