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

    /**
     * 排课信息新增接口
     *
     * @param scheduleRecordEntity 排课信息实体，包含排课的详细信息，需要进行验证
     * @param bindingResult 数据验证结果，用于检查scheduleRecordEntity的合法性
     * @return ResponseEntity<Map<String, Object>> 包含操作结果的状态码和数据信息
     */
    @PostMapping("/scheduleAdd.do")
    public ResponseEntity<Map<String, Object>> scheduleAdd(@Valid ScheduleRecordEntity scheduleRecordEntity, BindingResult bindingResult) {
        log.info("新增排课");
        Map<String, Object> returnData = new HashMap<>();
        // 验证实体信息是否有错误
        if (bindingResult.hasErrors()) {
            log.info("bean验证错误");
            // 如果有错误，将错误信息添加到返回数据中，并返回状态码400
            returnData.put("errorMap", BeanError.getErrorDataMap(bindingResult));
            returnData.put("code", 400);
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }
        // 设置创建时间和最后修改时间为当前时间
        scheduleRecordEntity.setCreateTime(LocalDateTime.now());
        scheduleRecordEntity.setLastModifyTime(LocalDateTime.now());
        // 保存排课信息
        if (scheduleRecordService.save(scheduleRecordEntity)) {
            // 保存成功，返回成功信息和状态码0
            returnData.put("code", 0);
            returnData.put("msg", "新增成功");
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        } else {
            // 保存失败，返回内部服务器错误状态码
            return new ResponseEntity<>(returnData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/refreshCache.do")
    public ResponseEntity<Map<String, Object>> refreshCache() {
        //todo
        return null;
    }

    /**
     * 调度并执行课程表的复制操作。
     *
     * @param sourceDateStr 源日期字符串，表示要复制的课程表的日期。
     * @param targetDateStr 目标日期字符串，表示要将课程复制到的日期。
     * @return 返回一个包含操作结果的状态码和消息的Map。
     *         如果复制成功，则"code"为0，无"msg"；
     *         如果复制失败，则"code"非0，"msg"包含错误信息。
     */
    @PostMapping("/scheduleCopy.do")
    public ResponseEntity<Map<String, Object>> scheduleCopy(@RequestParam("sourceDateStr") String sourceDateStr, @RequestParam("targetDateStr") String targetDateStr) {
        // 记录开始复制的日志
        log.info("复制课程表，复制 " + sourceDateStr+" 的课程到 "+targetDateStr);
        Map<String, Object> returnData = new HashMap<>();
        // 调用服务层方法执行课程表复制，并根据结果组装返回数据
        if (scheduleRecordService.copySchedule(sourceDateStr,targetDateStr)) {
            log.info("复制成功");
            // 复制成功，设置返回码为0
            returnData.put("code", 0);
            return new ResponseEntity<>(returnData,HttpStatus.OK);
        }else {
            log.info("复制失败");
            // 复制失败，设置错误消息
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

    /**
     * 提供根据排课ID获取排课详细信息的服务接口。
     *
     * @param id 排课的唯一标识符。
     * @return 返回一个ResponseEntity对象，其中包含一个Map<String, Object>类型的body。
     *         如果找到对应的排课信息，则"code"键的值为0，"data"键的值为排课详情对象。
     *         如果未找到对应的排课信息，则返回内部服务器错误状态码。
     */
    @PostMapping("/scheduleDetail.do")
    public ResponseEntity<Map<String, Object>> scheduleDetail(@RequestParam("id") Long id) {
        log.info("获取排课详细信息");
        Map<String, Object> returnData = new HashMap<>();
        // 通过ID查询排课详情
        ScheduleDetailsVo scheduleDetailsVo = scheduleRecordService.getScheduleDetailsVoById(id);
        if (scheduleDetailsVo != null) {
            // 排课详情存在，将信息放入返回数据中
            returnData.put("code", 0);
            returnData.put("data", scheduleDetailsVo);
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        } else {
            // 未找到排课详情，返回内部服务器错误
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}