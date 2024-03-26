package com.kclm.xsap.web.controller;

import com.kclm.xsap.consts.ReservationAction;
import com.kclm.xsap.model.entity.ReservationRecordEntity;
import com.kclm.xsap.service.GlobalReservationSetService;
import com.kclm.xsap.service.ReservationRecordService;
import com.kclm.xsap.utils.BeanError;
import com.kclm.xsap.utils.exception.ReservationFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/reserve")
public class ReserveController {
    @Resource
    private ReservationRecordService reservationRecordService;

    @Resource
    private GlobalReservationSetService globalReservationSetService;

    @PostMapping("/addReserve.do")
    public ResponseEntity<Map<String, Object>> addReserve(@Valid ReservationRecordEntity reservationRecordEntity, BindingResult bindingResult, HttpSession session) {
        log.info("预约课程addReserve.do");
        log.info(reservationRecordEntity.getMemberId() + reservationRecordEntity.getCardName());
        Map<String, Object> returnData = new HashMap<>();

        if (bindingResult.hasErrors()) {
            log.info("bean验证错误");
            returnData.put("msg", BeanError.getErrorData(bindingResult));
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }

        if (globalReservationSetService.isNotInPrescriptiveTime(reservationRecordEntity.getScheduleId(), ReservationAction.MAKE_RESERVATION)) {
            log.info("预约时间不在预约时间内");
            returnData.put("msg", "预约时间不在预约时间内");
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }


        reservationRecordEntity.setCreateTime(LocalDateTime.now());
        reservationRecordEntity.setLastModifyTime(LocalDateTime.now());
        try {
            if (reservationRecordService.doReserve(reservationRecordEntity)) {
                log.info("预约成功");
                returnData.put("code", 0);
                returnData.put("msg", "success");
                return new ResponseEntity<>(returnData, HttpStatus.OK);
            } else {
                log.error("预约失败");
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (ReservationFailedException e) {
            returnData.put("msg", e.getMessage());
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }
    }

    @PostMapping("/cancelReserve.do")
    public ResponseEntity<Map<String, Object>> cancelReserve(@RequestParam("reserveId") Long id) {
        log.info("预约课程cancelReserve.do,id =");
        Map<String, Object> returnData = new HashMap<>();

        if (globalReservationSetService.isNotInPrescriptiveTime(id, ReservationAction.CANCEL_RESERVATION)) {
            log.info("取消预约时间不在预约时间内");
            returnData.put("msg", "取消预约时间不在预约时间内");
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }

        if (reservationRecordService.cancelReservation(id)) {
            returnData.put("code", 0);
        } else {
            returnData.put("msg", "未知错误，请联系管理员！");
        }
        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }

    @PostMapping("/getReserveId.do")
    public ResponseEntity<Map<String, Object>> getReserveId(@RequestParam("memberId") Long memberId, @RequestParam("scheduleId") Long scheduleId) {
        Map<String, Object> returnData = new HashMap<>();
        returnData.put("id", reservationRecordService.getReserveId(memberId, scheduleId));
        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }


}
