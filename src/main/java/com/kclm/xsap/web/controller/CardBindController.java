package com.kclm.xsap.web.controller;

import com.kclm.xsap.service.CardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/cardBind")
public class CardBindController {

    private final static Logger log = LoggerFactory.getLogger(CardController.class);
    @Resource
    private CardService cardService;
    @GetMapping("/x_member_card_bind.do")
    public String toMemberCardBind(){
        log.info("前往会员卡绑定页面");
        return "member/x_member_card_bind";
    }


}
