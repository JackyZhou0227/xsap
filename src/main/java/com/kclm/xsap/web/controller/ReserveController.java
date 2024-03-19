package com.kclm.xsap.web.controller;

import com.kclm.xsap.model.entity.ReservationRecordEntity;
import com.kclm.xsap.service.ReservationRecordService;
import com.kclm.xsap.utils.BeanError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Controller
@RequestMapping("/reserve")
public class ReserveController {
    private final static Logger log = LoggerFactory.getLogger(ReserveController.class);
    @Resource
    private ReservationRecordService reservationRecordService;

    @PostMapping("/addReserve.do")
    public ResponseEntity<Map<String, Object>> addReserve(@Valid ReservationRecordEntity reservationRecordEntity, BindingResult bindingResult, HttpSession session) {
        log.info("预约课程addReserve.do");
        Map<String, Object> returnData = new HashMap<>();

        if (bindingResult.hasErrors()) {
            log.info("bean验证错误");
            returnData.put("msg", BeanError.getErrorData(bindingResult));
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }
        reservationRecordEntity.setCreateTime(LocalDateTime.now());
        reservationRecordEntity.setLastModifyTime(LocalDateTime.now());
        if (reservationRecordService.save(reservationRecordEntity)) {
            log.info("预约成功");
            returnData.put("code", 0);
            returnData.put("msg", "success");
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        } else {
            log.error("预约失败");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/cancelReserve.do")
    public ResponseEntity<Map<String, Object>> cancelReserve(@RequestParam("reserveId") Long id) {
        log.info("预约课程cancelReserve.do,id =");
        Map<String, Object> returnData = new HashMap<>();
        if (reservationRecordService.updateStatusById(id)) {
            returnData.put("code", 0);
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        } else {
            returnData.put("msg","未知错误，请联系管理员！");
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }
    }


}
