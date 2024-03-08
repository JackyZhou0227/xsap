package com.kclm.xsap.web.controller;

import com.kclm.xsap.model.entity.MemberCardEntity;
import com.kclm.xsap.service.MemberCardService;
import com.kclm.xsap.utils.BeanError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/card")
public class CardController {

    private final static Logger log = LoggerFactory.getLogger(CardController.class);
    @Resource
    private MemberCardService memberCardService;

    @GetMapping("/x_member_card.do")
    public String toMemberCard() {
        log.info("跳转至card页面");
        return "member/x_member_card";
    }

    @PostMapping("/cardList.do")
    public ResponseEntity<Map<String, Object>> cardList() {
        Map<String, Object> returnData = new HashMap<>();
        returnData.put("data", memberCardService.list());
        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }

    @GetMapping("/x_member_card_edit.do")
    public String toMemberCardEdit(@RequestParam("id") Long id, Model model) {
        log.info("跳转至card编辑页面,id=" + id);
        MemberCardEntity cardMsg = memberCardService.getById(id);
        model.addAttribute("cardMsg", cardMsg);
        return "member/x_member_card_edit";
    }

    @PostMapping("/cardEdit.do")
    public ResponseEntity<Map<String, Object>> cardEdit(@Valid MemberCardEntity cardMsg, BindingResult bindingResult) {
        Map<String, Object> returnData = new HashMap<>();
        if (bindingResult.hasErrors()) {
            String errorData = BeanError.getErrorData(bindingResult);
            returnData.put("msg",errorData);
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }
        return null;
    }


    @PostMapping("/deleteOne.do")
    public ResponseEntity<Map<String, Object>> deleteOne(@RequestParam("id") Long id) {
        Map<String, Object> returnData = new HashMap<>();
        if (memberCardService.removeById(id)) {
            returnData.put("status", "success");
            returnData.put("message", "删除成功");
        } else {
            returnData.put("status", "error");
            returnData.put("message", "删除失败");
        }
        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }


}