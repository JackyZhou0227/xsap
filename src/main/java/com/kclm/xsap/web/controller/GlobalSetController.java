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
import java.time.LocalDateTime;
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

    /**
     * 更新全局设置信息
     *
     * @param set 全局预留设置实体对象，包含需要更新的设置信息
     * @return 返回一个响应实体，包含更新操作的状态码和消息
     */
    @PostMapping("/globalSetUpdate.do")
    public ResponseEntity<Map<String, Object>> globalSetUpdate(GlobalReservationSetEntity set) {
        log.info("更新全局设置");
        Map<String, Object> returnData = new HashMap<>();
        set.setLastModifyTime(LocalDateTime.now()); // 更新最后修改时间

        // 尝试更新全局设置信息
        if (globalReservationSetService.updateById(set)) {
            log.info("更新成功");
            returnData.put("code", 0); // 更新成功状态码
            returnData.put("msg", "更新成功"); // 更新成功消息
        } else {
            log.error("更新全局设置失败");
            returnData.put("msg", "更新失败"); // 更新失败消息
        }

        // 返回更新操作的结果
        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }

}