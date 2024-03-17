package com.kclm.xsap.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kclm.xsap.model.entity.EmployeeEntity;
import com.kclm.xsap.model.vo.register.RegisterVo;
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
import java.time.LocalDateTime;
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

    /**
     * 注册页面的跳转
     *
     * @return 注册页面的视图名称
     */
    @GetMapping("/toRegister")
    public String toRegister() {
        log.info("前往注册页面");
        return "x_register";
    }

    /**
     * 用户注册
     *
     * @param userName  用户名
     * @param password  密码
     * @param pwd2      再次输入密码
     * @param model     模型
     * @return 登录页面
     */
    @PostMapping("/register")
    public String register(@RequestParam("userName") String userName, @RequestParam("password") String password,
                           @RequestParam("pwd2") String pwd2, Model model) {
        log.debug("用户进行注册操作，参数分别是：userName:" + userName + ",password:" + password + ",pwd2:" + pwd2);

        if (employeeService.isUsernameExists(userName)) {
            model.addAttribute("CHECK_TYPE_ERROR", 0);
            return "x_register";
        }
        if (!password.equals(pwd2)) {
            model.addAttribute("CHECK_TYPE_ERROR", 1);
            return "x_register";
        }
        RegisterVo registerVo = new RegisterVo(userName,password,pwd2);
        if (employeeService.isrRegisterSuccess(registerVo)){
            log.error("注册成功");
            return "x_login";
        }else {
            log.error("注册失败");
            return "x_register";
        }
    }

    @GetMapping("/toEnsureUser")
    public String toEnsureUser() {
        log.info("前往重置密码页面");
        return "x_ensure_user";
    }

    /**
     * 用户重置密码页面的跳转逻辑。
     *
     * @param userPhoneOrEmail 用户输入的手机号或邮箱，用于重置密码。
     * @param model Spring模型，用于在视图和控制器之间传递数据。
     * @return 返回字符串路径，根据逻辑跳转到不同的页面。
     */
    @GetMapping("/toResetPwd")
    public String toResetPwd(@RequestParam("userPhoneOrEmail") String userPhoneOrEmail, Model model) {
        log.debug("用户重置密码,输入手机号或邮箱", userPhoneOrEmail);

        // 定义邮箱和手机号的正则表达式
        String emailRegex = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        String phoneRegex = "^1[0-9]{10}$";

        // 判断输入是邮箱还是手机号
        boolean isAEmail = Pattern.compile(emailRegex).matcher(userPhoneOrEmail).matches();
        boolean isAPhone = Pattern.compile(phoneRegex).matcher(userPhoneOrEmail).matches();
        if (isAEmail || isAPhone) {
            // 根据邮箱或手机号查询用户信息
            List<EmployeeEntity> userOne = employeeService.list(new QueryWrapper<EmployeeEntity>().eq("role_email", userPhoneOrEmail).or().eq("phone", userPhoneOrEmail));

            if (userOne.isEmpty()) {
                // 如果未查询到用户信息，则将错误信息添加到模型中，返回到特定页面
                model.addAttribute("CHECK_USER_ERROR", true);
                return "x_ensure_user";
            }

            if (isAEmail) {
                // 如果是邮箱，理论上应该发送邮件给用户（该部分代码未实现）

                // 假设邮件发送成功，返回相应页面
                return "send_mail_ok";
            }

            // 如果是手机号，同样假设发送邮件成功，返回相应页面
            return "send_mail_ok";
        } else {
            // 如果输入格式不正确，将错误信息添加到模型中，返回到特定页面进行提示
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
