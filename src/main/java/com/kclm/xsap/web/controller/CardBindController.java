package com.kclm.xsap.web.controller;

import com.kclm.xsap.model.entity.MemberBindRecordEntity;
import com.kclm.xsap.service.MemberBindRecordService;
import com.kclm.xsap.service.MemberCardService;
import com.kclm.xsap.utils.BeanError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/cardBind")
public class CardBindController {

    private final static Logger log = LoggerFactory.getLogger(CardController.class);
    @Resource
    private MemberCardService memberCardService;

    @Resource
    private MemberBindRecordService memberBindRecordService;

    @GetMapping("/x_member_card_bind.do")
    public String toMemberCardBind() {
        log.info("前往会员卡绑定页面");
        return "member/x_member_card_bind";
    }

    @GetMapping("/x_member_bind.do")
    public String toMemberBind() {
        log.info("前往会员绑定页面");
        return "member/x_member_bind";
    }

    @PostMapping("/memberBind.do")
    public ResponseEntity<Map<String, Object>> memberBind(@Valid MemberBindRecordEntity memberBindRecordEntity,
                                                          BindingResult bindingResult) {

        log.info("会员绑定,memberId = " + memberBindRecordEntity.getMemberId() + "; cardId = " + memberBindRecordEntity.getCardId());
        Map<String, Object> returnData = new HashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Bean验证错误");
            Map<String, String> errorMap = BeanError.getErrorDataMap(bindingResult);
            returnData.put("code", 400);
            returnData.put("errorMap", errorMap);
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }
        memberBindRecordEntity.setCreateTime(LocalDateTime.now());
        memberBindRecordEntity.setLastModifyTime(LocalDateTime.now());
        if (memberBindRecordService.save(memberBindRecordEntity)) {
            log.info("绑定成功");
            returnData.put("code", 0);
            return ResponseEntity.ok(returnData);
        } else {
            log.error("未知错误，绑定失败！");
            returnData.put("msg", "未知错误，请联系管理员！");
            return new ResponseEntity<>(returnData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}