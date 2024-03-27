package com.kclm.xsap.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kclm.xsap.model.entity.ConsumeRecordEntity;
import com.kclm.xsap.model.vo.MemberCardStatisticsWithTotalDataInfoVo;
import com.kclm.xsap.model.vo.statistics.CardCostVo;
import com.kclm.xsap.model.vo.statistics.ClassCostVo;
import com.kclm.xsap.model.vo.statistics.StatisticsOfCardCostVo;
import com.kclm.xsap.service.ConsumeRecordService;
import com.kclm.xsap.service.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/statistics")
public class StatisticsController {

    @Resource
    private StatisticsService statisticsService;

    @Resource
    private ConsumeRecordService consumeRecordService;

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


    //会员卡统计
    @PostMapping("/cardInfo.do")
    public ResponseEntity<Map<String, Object>> cardInfo() {
        log.info("会员卡统计");

        Map<String, Object> returnData = new HashMap<>();
        MemberCardStatisticsWithTotalDataInfoVo memberCardStatisticsWithTotalDataInfoVo = statisticsService.getCardInfoStatistics();

        if (memberCardStatisticsWithTotalDataInfoVo != null) {
            returnData.put("data", memberCardStatisticsWithTotalDataInfoVo);
        } else {
            returnData.put("code", 400);
            returnData.put("msg", "没查到任何数据！");
        }

        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }

    @PostMapping("/yearList")
    public ResponseEntity<Map<String, Object>> yearList() {
        Map<String, Object> returnData = new HashMap<>();
        //查出消费记录的年份
        List<ConsumeRecordEntity> consumeRecordEntityList = consumeRecordService.list(new QueryWrapper<ConsumeRecordEntity>()
                .select("create_time")
                .orderByAsc("create_time"));
        int minMonth = consumeRecordEntityList.get(0).getCreateTime().getYear();
        List<Integer> yearList = new ArrayList<>();
        for (int i = minMonth; i <= LocalDate.now().getYear(); i++) {
            yearList.add(i);
        }
        returnData.put("data", yearList);
        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }


    //收费统计
    @PostMapping("/cardCostMonthOrSeasonOrYear")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> cardCostMonthOrSeasonOrYear(StatisticsOfCardCostVo statisticsOfCardCostVo) {
        log.info("收费统计");
        Map<String, Object> returnData = new HashMap<>();
        Integer beginYear = statisticsOfCardCostVo.getBeginYear();
        Integer endYear = statisticsOfCardCostVo.getEndYear();

        if (endYear < beginYear) {
            returnData.put("msg", "起始时间与结束时间冲突！请修改后再查看统计结果");
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        } else {
            CardCostVo cardCostVo = statisticsService.getcardCostVo(statisticsOfCardCostVo);
            if (cardCostVo == null) {
                returnData.put("msg", "你所查询的时间段没有数据！");
            } else {
                returnData.put("data", cardCostVo);
                returnData.put("code", 0);
            }
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }
    }


    //课消统计
    @PostMapping("/classCostMonthOrSeasonOrYear")
    public ResponseEntity<Map<String, Object>> classCostMonthOrSeasonOrYear(StatisticsOfCardCostVo statisticsOfCardCostVo) {
        log.info("课消统计");
        Map<String, Object> returnData = new HashMap<>();
        Integer beginYear = statisticsOfCardCostVo.getBeginYear();
        Integer endYear = statisticsOfCardCostVo.getEndYear();
        if (endYear < beginYear) {
            returnData.put("msg", "起始时间与结束时间冲突！请修改后再查看统计结果");
        } else {
            ClassCostVo classCostVo = statisticsService.getclassCostVo(statisticsOfCardCostVo);
            if (classCostVo == null) {
                returnData.put("msg", "你所查询的时间段没有数据！");
            } else {
                returnData.put("code", 0);
                returnData.put("data", classCostVo);
            }
        }
        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }

    //总课次统计
    @PostMapping("/classCountMonthOrSeasonOrYear")
    public ResponseEntity<Map<String, Object>> classCountMonthOrSeasonOrYear(StatisticsOfCardCostVo statisticsOfCardCostVo) {
        log.info("总课次统计");
        Map<String, Object> returnData = new HashMap<>();
        Integer beginYear = statisticsOfCardCostVo.getBeginYear();
        Integer endYear = statisticsOfCardCostVo.getEndYear();

        if (endYear < beginYear) {
            returnData.put("msg", "起始时间与结束时间冲突！请修改后再查看统计结果");
        } else {
            CardCostVo countCostVo = statisticsService.getCountCostVo(statisticsOfCardCostVo);
            if (countCostVo == null) {
                returnData.put("msg", "你所查询的时间段没有数据！");

            } else {
                returnData.put("data", countCostVo);
                returnData.put("code", 0);
            }
        }
        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }

    //新增与流失统计
    @PostMapping("/addAndStreamCountMonthOrSeasonOrYear")
    public ResponseEntity<Map<String, Object>> addAndStreamCountMonthOrSeasonOrYear(StatisticsOfCardCostVo statisticsOfCardCostVo) {
        log.info("新增与流失统计");
        Map<String, Object> returnData = new HashMap<>();
        Integer beginYear = statisticsOfCardCostVo.getBeginYear();
        Integer endYear = statisticsOfCardCostVo.getEndYear();

        if (endYear < beginYear) {
            returnData.put("msg", "起始时间与结束时间冲突！请修改后再查看统计结果");
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        } else {
            CardCostVo countCostVo = statisticsService.getCountNum(statisticsOfCardCostVo);
            if (countCostVo == null) {
                returnData.put("msg", "你所查询的时间段没有数据");

            } else {
                returnData.put("code", 0);
                returnData.put("data", countCostVo);
            }
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }
    }

    @GetMapping("refreshCache.do")
    public ResponseEntity<Map<String, Object>> refreshCache() {
        log.info("会员卡统计刷新缓存");
        Map<String, Object> returnData = new HashMap<>();

        returnData.put("code", 0);
        return new ResponseEntity<>(returnData, HttpStatus.OK);

    }
}
