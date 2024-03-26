package com.kclm.xsap.web.controller;

import com.kclm.xsap.model.entity.CourseEntity;
import com.kclm.xsap.model.entity.ScheduleRecordEntity;
import com.kclm.xsap.model.vo.ConsumeEnsureVo;
import com.kclm.xsap.model.vo.CourseScheduleVo;
import com.kclm.xsap.model.vo.ScheduleDetailReservedVo;
import com.kclm.xsap.model.vo.ScheduleDetailsVo;
import com.kclm.xsap.service.*;
import com.kclm.xsap.utils.BeanError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    @Resource
    private ScheduleRecordService scheduleRecordService;

    @Resource
    private ReservationRecordService reservationRecordService;

    @Resource
    private ClassRecordService classRecordService;

    @Resource
    private CourseService courseService;

    @Resource
    private MemberLogService memberLogService;

    @GetMapping("/x_course_schedule.do")
    public String toCourseSchedule() {
        return "course/x_course_schedule";
    }

    @PostMapping("/scheduleList.do")
    @ResponseBody
    public List<CourseScheduleVo> scheduleList() {
        return scheduleRecordService.listAllCourseScheduleVo();
    }

    /**
     * 排课信息新增接口
     *
     * @param scheduleRecordEntity 排课信息实体，包含排课的详细信息，需要进行验证
     * @param bindingResult        数据验证结果，用于检查scheduleRecordEntity的合法性
     * @return ResponseEntity<Map < String, Object>> 包含操作结果的状态码和数据信息
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
        if(scheduleRecordService.isTeacherBusy(scheduleRecordEntity)){
            returnData.put("msg", "该老师已被占用");
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }
        CourseEntity courseEntity = courseService.getById(scheduleRecordEntity.getCourseId());

        // 设置创建时间和最后修改时间为当前时间
        scheduleRecordEntity.setCreateTime(LocalDateTime.now());
        scheduleRecordEntity.setLimitAge(courseEntity.getLimitAge());
        scheduleRecordEntity.setLimitSex(courseEntity.getLimitSex());
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

    /**
     * 调度并执行课程表的复制操作。
     *
     * @param sourceDateStr 源日期字符串，表示要复制的课程表的日期。
     * @param targetDateStr 目标日期字符串，表示要将课程复制到的日期。
     * @return 返回一个包含操作结果的状态码和消息的Map。
     * 如果复制成功，则"code"为0，无"msg"；
     * 如果复制失败，则"code"非0，"msg"包含错误信息。
     */
    @PostMapping("/scheduleCopy.do")
    public ResponseEntity<Map<String, Object>> scheduleCopy(@RequestParam("sourceDateStr") String sourceDateStr, @RequestParam("targetDateStr") String targetDateStr) {
        // 记录开始复制的日志
        log.info("复制课程表，复制 " + sourceDateStr + " 的课程到 " + targetDateStr);
        Map<String, Object> returnData = new HashMap<>();
        // 调用服务层方法执行课程表复制，并根据结果组装返回数据
        if (scheduleRecordService.copySchedule(sourceDateStr, targetDateStr)) {
            log.info("复制成功");
            // 复制成功，设置返回码为0
            returnData.put("code", 0);
        } else {
            log.info("复制失败");
            // 复制失败，设置错误消息
            returnData.put("msg", "请选择有排课的日期复制！");
        }
        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }

    @GetMapping("/x_course_schedule_detail.do")
    public String toCourseScheduleDetail(@RequestParam("id") Long id, Model model) {
        log.info("进入排课详细信息页面");
//        scheduleRecordService.initClassRecord(id);
        model.addAttribute("ID", id);
        return "course/x_course_schedule_detail";
    }

    /**
     * 提供根据排课ID获取排课详细信息的服务接口。
     *
     * @param id 排课的唯一标识符。
     * @return 返回一个ResponseEntity对象，其中包含一个Map<String, Object>类型的body。
     * 如果找到对应的排课信息，则"code"键的值为0，"data"键的值为排课详情对象。
     * 如果未找到对应的排课信息，则返回内部服务器错误状态码。
     */
    @PostMapping("/scheduleDetail.do")
    public ResponseEntity<Map<String, Object>> scheduleDetail(@RequestParam("id") Long id) {
        log.info("获取排课详细信息");
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

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

    @PostMapping("/reservedList.do")
    public ResponseEntity<Map<String, Object>> reservedList(@RequestParam("id") Long id) {
        log.info("已预约信息，id=" + id);
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Map<String, Object> returnData = new HashMap<>();
        List<ScheduleDetailReservedVo> scheduleDetailReservedVoList = reservationRecordService.getScheduleDetailReservedVoByScheduleId(id);
        scheduleDetailReservedVoList.removeIf(scheduleDetailReservedVo -> scheduleDetailReservedVo.getReserveStatus() != 0);
        returnData.put("data", scheduleDetailReservedVoList);
        return new ResponseEntity<>(returnData, HttpStatus.OK);

    }

    @PostMapping("/reserveRecord.do")
    public ResponseEntity<Map<String, Object>> reserveRecord(@RequestParam("id") Long id) {
        log.info("查看预约记录，id=" + id);
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Map<String, Object> returnData = new HashMap<>();
        List<ScheduleDetailReservedVo> scheduleDetailReservedVoList = reservationRecordService.getScheduleDetailReservedVoByScheduleId(id);

        returnData.put("data", scheduleDetailReservedVoList);
        return new ResponseEntity<>(returnData, HttpStatus.OK);

    }

    @PostMapping("/classRecord.do")
    public ResponseEntity<Map<String, Object>> classRecord(@RequestParam("id") Long id) {
        log.warn("查看上课数据，id=" + id);
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Map<String, Object> returnData = new HashMap<>();
        returnData.put("data", classRecordService.getClassRecordVoListByScheduleId(id));
        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }

    @PostMapping("/deleteOne.do")
    public ResponseEntity<Map<String, Object>> deleteOne(@RequestParam("id") Long id) {
        log.info("删除排课，id=" + id);
        Map<String, Object> returnData = new HashMap<>();
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<ScheduleDetailReservedVo> scheduleDetailReservedVoList = reservationRecordService.getScheduleDetailReservedVoByScheduleId(id);
        scheduleDetailReservedVoList.removeIf(scheduleDetailReservedVo -> scheduleDetailReservedVo.getReserveStatus() != 0);
        if (classRecordService.getClassRecordVoListByScheduleId(id).isEmpty() && scheduleDetailReservedVoList.isEmpty()) {
            if (scheduleRecordService.removeById(id)) {
                returnData.put("code", 0);
                return new ResponseEntity<>(returnData, HttpStatus.OK);
            } else {
                log.error("删除排课失败");
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }


    @GetMapping("/toSearch.do")
    public ResponseEntity<Map<String, Object>> toSearch(@RequestParam("keyword") String keyword) {
        log.info("扣费操作，搜索课程，keyword=" + keyword);
        Map<String, Object> returnData = new HashMap<>();
        returnData.put("value", scheduleRecordService.getScheduleForConsumeSearchVoByKeyword(keyword));
        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }


    @PostMapping("/consumeEnsureAll.do")
    public ResponseEntity<Map<String, Object>> consumeEnsureAll(@RequestParam("scheduleId") Long scheduleId, @RequestParam("operator") String operator) {
        log.info("一键扣费");
        if (scheduleId == null || operator == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Map<String, Object> returnData = new HashMap<>();
        if (memberLogService.consumeEnsureAll(scheduleId, operator)) {
            returnData.put("code", 0);
            returnData.put("msg", "扣费成功");
        } else {
            returnData.put("msg", "未知错误，请联系管理员！");
        }
        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }


    @PostMapping("/queryAmountsPayable.do")
    public ResponseEntity<Map<String, Object>> queryAmountsPayable(@RequestParam("classId") Long classId) {
        Map<String, Object> returnData = new HashMap<>();
        Integer amountsPayable = classRecordService.getAmountsPayableByClassId(classId);
        if (amountsPayable != null) {
            log.info("应扣次数：" + amountsPayable);
            returnData.put("code", 0);
            returnData.put("data", amountsPayable);
        } else {
            returnData.put("msg", "没有查到应扣次数值！");
        }
        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }

    @PostMapping("/consumeEnsure.do")
    public ResponseEntity<Map<String, Object>> consumeEnsure(@Valid ConsumeEnsureVo consumeEnsureVo, BindingResult bindingResult) {
        log.info("课程单独扣费操作");
        Map<String, Object> returnData = new HashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("课程单独扣费操作bean验证错误");
            returnData.put("msg", BeanError.getErrorData(bindingResult));
            returnData.put("code", 400);
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }
        if (consumeEnsureVo.getClassId() == null || consumeEnsureVo.getScheduleId() == null || consumeEnsureVo.getOperator().isEmpty()) {
            log.warn("课程单独扣费操作参数不合法!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (memberLogService.consumeEnsure(consumeEnsureVo)) {
            returnData.put("code", 0);
            returnData.put("msg", "扣费成功");
        } else {
            returnData.put("msg", "未知错误，请联系管理员！");
        }
        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }
}