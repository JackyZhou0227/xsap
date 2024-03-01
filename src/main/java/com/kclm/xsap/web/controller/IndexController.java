package com.kclm.xsap.web.controller;

import com.kclm.xsap.entity.EmployeeEntity;
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

@Controller
@RequestMapping("/index")
public class IndexController {

    @Resource
    private EmployeeService employeeService;

    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/x_index_home.do")
    public String x_index_home() {
        log.info("进入x_index_home");
        return "x_index_home";
    }
    /**
     * 用户退出登录
     *
     * @param session HttpSession对象
     * @return 登录页面
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        log.debug("用户退出登录");
        session.removeAttribute("LOGIN_USER");
        return "x_login";
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
        EmployeeEntity user = new EmployeeEntity();
        user.setRoleName(userName);
        user.setRolePassword(password);
        user.setCreateTime(LocalDateTime.now());
        user.setLastModifyTime(LocalDateTime.now());
        employeeService.save(user);
        log.debug("注册成功");
        model.addAttribute("REGISTER_SUCCESS", "注册成功");
        return "x_login";
    }

}
