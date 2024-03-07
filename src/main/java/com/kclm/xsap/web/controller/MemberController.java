package com.kclm.xsap.web.controller;

import com.kclm.xsap.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/member")
public class MemberController {

    private final static Logger log = LoggerFactory.getLogger(MemberController.class);
    @Resource
    private MemberService memberService;

    @GetMapping("/x_member_list.do")
    public String toMemberList() {
        log.info("toMemberList");
        return "member/x_member_list";
    }

    @PostMapping("/memberList.do")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> memberList() {
        Map<String,Object> returnData = new HashMap<>();
        returnData.put("memberList", memberService.getMemberVoList());

        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }
}