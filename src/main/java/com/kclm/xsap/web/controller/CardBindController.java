package com.kclm.xsap.web.controller;

import com.kclm.xsap.model.entity.MemberBindRecordEntity;
import com.kclm.xsap.service.MemberBindRecordService;
import com.kclm.xsap.service.MemberCardService;
import com.kclm.xsap.utils.BeanError;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Controller
@RequestMapping("/cardBind")
public class CardBindController {

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

    /**
     * 会员绑定接口
     *
     * @param memberBindRecordEntity 包含会员绑定信息的实体对象，必须经过验证
     * @param bindingResult 验证结果，用于存放验证过程中产生的错误信息
     * @return ResponseEntity 包含操作结果的响应实体，包括错误信息或成功标识
     */
    @PostMapping("/memberBind.do")
    public ResponseEntity<Map<String, Object>> memberBind(@Valid MemberBindRecordEntity memberBindRecordEntity,
                                                          BindingResult bindingResult) {
        // 记录会员绑定信息日志
        log.info("会员绑定,memberId = " + memberBindRecordEntity.getMemberId() + "; cardId = " + memberBindRecordEntity.getCardId());
        Map<String, Object> returnData = new HashMap<>();
        // 检查验证结果，若有错误则返回错误信息
        if (bindingResult.hasErrors()) {
            log.info("Bean验证错误");
            // 将验证错误信息转换为Map格式，方便返回给前端
            Map<String, String> errorMap = BeanError.getErrorDataMap(bindingResult);
            returnData.put("code", 400);
            returnData.put("errorMap", errorMap);
            return ResponseEntity.ok(returnData);
        }
        // 设置创建时间和最后修改时间为当前时间
        memberBindRecordEntity.setCreateTime(LocalDateTime.now());
        memberBindRecordEntity.setLastModifyTime(LocalDateTime.now());
        // 保存会员绑定信息，若成功则返回成功标识
        if (memberBindRecordService.doBind(memberBindRecordEntity)) {
            log.info("绑定成功");
            returnData.put("code", 0);
            return ResponseEntity.ok(returnData);
        } else {
            // 绑定失败，记录错误日志并返回未知错误信息
            log.error("未知错误，绑定失败！");
            returnData.put("msg", "未知错误，请联系管理员！");
            return new ResponseEntity<>(returnData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}