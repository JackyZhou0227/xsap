package com.kclm.xsap.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kclm.xsap.model.entity.EmployeeEntity;
import com.kclm.xsap.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/user")
public class UserController {

    private final static Logger log = LoggerFactory.getLogger(UserController.class);
    @Resource
    private EmployeeService employeeService;

    /**
     * 跳转到登录页面
     *
     * @return 登录页面的视图名称
     */
    @GetMapping("/toLogin")
    public String toLogin() {
        return "x_login";
    }

    /**
     * 登录请求处理方法
     *
     * @param name      用户名
     * @param pwd       密码
     * @param checkCode 验证码
     * @param model     模型
     * @param session   会话
     * @return 登录成功后重定向到book/list.do，登录失败后返回user/login
     */
    @PostMapping("/login")
    public String login(@RequestParam("username") String name, @RequestParam("password") String pwd,
                        @RequestParam("checkcode") String checkCode, Model model, HttpSession session) {
        log.info("登录请求的三个参数：" + name + "," + pwd + "," + checkCode);

        EmployeeEntity user = this.employeeService.isExistEmp(name, pwd);
        if (user == null) {
            log.info("用户输入用户名或密码错误");
            model.addAttribute("USER_NOT_EXIST", true);
            return "x_login";
        } else {
            log.info("登录成功..");
            model.addAttribute("USER_NOT_EXIST", false);
            session.setAttribute("LOGIN_USER", user);
            //return "book/list";
            return "redirect:/index";

        }
    }

    @GetMapping("/toEnsureUser")
    public String toEnsureUser() {
        log.info("前往重置密码页面");
        return "x_ensure_user";
    }

    @GetMapping("/toResetPwd")
    public String toResetPwd(@RequestParam("userPhoneOrEmail") String userPhoneOrEmail, Model model) {
        log.debug("用户重置密码,输入手机号或邮箱", userPhoneOrEmail);

        String emailRegex = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        String phoneRegex = "^1[0-9]{10}$";

        boolean isAEmail = Pattern.compile(emailRegex).matcher(userPhoneOrEmail).matches();
        boolean isAPhone = Pattern.compile(phoneRegex).matcher(userPhoneOrEmail).matches();
        if (isAEmail || isAPhone) {
            //根据邮箱查询该用户     //由于保存的时候没有设置邮箱去重检查，所以假设可以存在相同邮箱【相容用户】
            List<EmployeeEntity> userOne = employeeService.list(new QueryWrapper<EmployeeEntity>().eq("role_email", userPhoneOrEmail).or().eq("phone", userPhoneOrEmail));
            //由于可能会用到该用户的信息，故不用count查个数
            if (userOne.isEmpty()) {
                //没查到该用户信息,返回提示到前台
                model.addAttribute("CHECK_USER_ERROR", true);
                return "x_ensure_user";
            }
            if (isAEmail) {
                //todo 发送邮件

                return "send_mail_ok";
            }

            //查到信息，跳转页面
            return "send_mail_ok";
        } else {
            //格式不正确，返回提示到前台
            model.addAttribute("CHECK_INPUT_FORMAT", true);

            return "x_ensure_user";
        }
    }

    @GetMapping("/toResetPage")
    public String toResetPage() {
        log.info("前往重置密码页面");
        return "x_reset_passward";
    }

    @GetMapping("/resetPassword")
    public String resetPassword(@RequestParam("newPwd") String newPwd, @RequestParam("pwd2") String pwd2, Model model) {

        return null;
    }
}
