package com.kclm.xsap.web.controller;

import com.kclm.xsap.model.entity.ScheduleRecordEntity;
import com.kclm.xsap.model.vo.CourseScheduleVo;
import com.kclm.xsap.model.vo.ScheduleDetailsVo;
import com.kclm.xsap.service.ScheduleRecordService;
import com.kclm.xsap.utils.BeanError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
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
    @ResponseBody
    public List<CourseScheduleVo> scheduleList(HttpServletRequest request) {
        return scheduleRecordService.listAllCourseScheduleVo();
    }

    @PostMapping("/scheduleAdd.do")
    public ResponseEntity<Map<String, Object>> scheduleAdd(@Valid ScheduleRecordEntity scheduleRecordEntity, BindingResult bindingResult) {
        log.info("新增排课");
        Map<String, Object> returnData = new HashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("bean验证错误");
            returnData.put("errorMap", BeanError.getErrorDataMap(bindingResult));
            returnData.put("code", 400);
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }
        scheduleRecordEntity.setCreateTime(LocalDateTime.now());
        scheduleRecordEntity.setLastModifyTime(LocalDateTime.now());
        if (scheduleRecordService.save(scheduleRecordEntity)) {
            returnData.put("code", 0);
            returnData.put("msg", "新增成功");
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(returnData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/refreshCache.do")
    public ResponseEntity<Map<String, Object>> refreshCache() {
        //todo
        return null;
    }

    @PostMapping("/scheduleCopy.do")
    public ResponseEntity<Map<String, Object>> scheduleCopy(@RequestParam("sourceDateStr") String sourceDateStr, @RequestParam("targetDateStr") String targetDateStr) {
        //todo
        log.info("复制课程表，复制 " + sourceDateStr+" 的课程到 "+targetDateStr);
        Map<String, Object> returnData = new HashMap<>();
        if (scheduleRecordService.copySchedule(sourceDateStr,targetDateStr)) {
            log.info("复制成功");
            returnData.put("code", 0);
            return new ResponseEntity<>(returnData,HttpStatus.OK);
        }else {
            log.info("复制失败");
            returnData.put("msg", "请选择有排课的日期复制！");
            return new ResponseEntity<>(returnData,HttpStatus.OK);
        }
    }

    @GetMapping("/x_course_schedule_detail.do")
    public String toCourseScheduleDetail(@RequestParam("id") String id, Model model) {
        log.info("进入排课详细信息页面");
        model.addAttribute("ID",id);
        return "course/x_course_schedule_detail";
    }
    @PostMapping("/scheduleDetail.do")
    public ResponseEntity<Map<String, Object>> scheduleDetail(@RequestParam("id") Long id) {
        log.info("获取排课详细信息");
        Map<String, Object> returnData = new HashMap<>();
        ScheduleDetailsVo scheduleDetailsVo = scheduleRecordService.getScheduleDetailsVoById(id);
        if (scheduleDetailsVo != null) {
            returnData.put("code", 0);
            returnData.put("data", scheduleDetailsVo);
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}