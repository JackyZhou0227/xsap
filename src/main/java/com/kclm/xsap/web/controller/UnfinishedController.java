package com.kclm.xsap.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/unfinished")
public class UnfinishedController {

    @GetMapping("/x_wechat.do")
    public String toWeChat() {
        return "x_wechat";
    }

    @GetMapping("/x_wechat-1.do")
    public String toWeChat1() {
        return "x_wechat-1";
    }

    @GetMapping("/x_app_store.do")
    public String toAppStore() {
        return "x_app_store";
    }
}
