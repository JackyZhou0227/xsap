package com.kclm.xsap.web.controller;

import com.kclm.xsap.model.entity.MemberEntity;
import com.kclm.xsap.service.MemberService;
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
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
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
        Map<String, Object> returnData = new HashMap<>();
        returnData.put("memberList", memberService.getMemberVoList());
        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }

    @GetMapping("/x_member_add.do")
    public String toMemberAdd() {
        log.info("前往member_add页面");
        return "member/x_member_add";
    }

    @PostMapping("/memberAdd.do")
    public ResponseEntity<Map<String, Object>> memberAdd(@Valid MemberEntity memberMsg, BindingResult bindingResult) {
        log.info("添加会员");
        Map<String, Object> returnData = new HashMap<>();
        Map<String, String> errorMap = new HashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("bean验证错误");
            errorMap = BeanError.getErrorDataMap(bindingResult);
            returnData.put("errorMap", errorMap);
            returnData.put("code", 400);
            return new ResponseEntity<>(returnData, HttpStatus.BAD_REQUEST);
        }
        if (memberService.isPhoneExists(memberMsg.getPhone())) {
            log.info("手机号已存在");
            returnData.put("code", 400);
            errorMap.put("phone", "该手机号码已存在");
            returnData.put("errorMap", errorMap);
            return new ResponseEntity<>(returnData, HttpStatus.BAD_REQUEST);
        }
        memberMsg.setCreateTime(LocalDateTime.now());
        memberMsg.setLastModifyTime(LocalDateTime.now());

        if (memberService.save(memberMsg)) {
            returnData.put("code", 0);
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        } else {
            log.error("保存失败，未知错误");
            return new ResponseEntity<>(returnData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/x_member_list_details.do")
    public String toMemberListDetails(@RequestParam("id") Long id, Model model) {

        log.info("前往member_list_details页面，id=" + id);
//        model.addAttribute();
        return "member/x_member_list_details";
    }

    @GetMapping("/toSearcherAll.do")
    @ResponseBody
    public List<MemberEntity> toSearcherAll(@RequestParam String keyword) {
        //todo 接收就有问题 No mapping for GET /member/toSearcherAll.do123
        return memberService.searchMembersByNameOrPhone(keyword);
    }
}