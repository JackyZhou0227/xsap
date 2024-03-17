package com.kclm.xsap.web.controller;

import com.kclm.xsap.model.entity.EmployeeEntity;
import com.kclm.xsap.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/index")
public class IndexController {

    private final static Logger log = LoggerFactory.getLogger(IndexController.class);
    @Resource
    private EmployeeService employeeService;

    @GetMapping
    public String index() {

        return "index";
    }

    @GetMapping("/x_index_home.do")
    public String x_index_home() {
        log.info("进入x_index_home");
        return "x_index_home";
    }

    @GetMapping("/x_modify_password.do")
    public String toModifyPassword() {
        log.info("主页进入修改密码页面");
        return "x_modify_password";
    }

    @GetMapping("/x_profile.do")
    public String toProfile(@RequestParam("id") Long id, Model model) {
        log.info("进入个人资料页面");
        EmployeeEntity employeeEntity = employeeService.getById(id);
        model.addAttribute("userInfo", employeeEntity);
        return "x_profile";
    }

    /**
     * 修改用户信息
     *
     * @param employeeEntity 包含更新后用户信息的实体类
     * @param model 用于在视图和控制器之间传递数据
     * @return 返回视图的名称，根据修改结果决定是重定向到个人资料页面还是首页
     */
    @PostMapping("/modifyUser.do")
    public String modifyUser(@RequestBody EmployeeEntity employeeEntity, Model model) {
        log.info("修改用户信息");
        // 根据ID获取原始用户信息
        EmployeeEntity originMessage = employeeService.getById(employeeEntity.getId());
        // 如果新手机号已存在且与原手机号不同，则更新失败
        if (employeeService.isPhoneExists(employeeEntity.getPhone()) && !originMessage.getPhone().equals(employeeEntity.getPhone())) {
            log.info("手机号已存在");
            model.addAttribute("CHECK_PHONE_EXIST", true);
            // 返回个人资料页面，提示手机已存在
            return "x_profile";
        }
        // 设置最后修改时间
        employeeEntity.setLastModifyTime(LocalDateTime.now());
        // 更新用户信息
        if (employeeService.updateById(employeeEntity)) {
            log.info("用户修改个人资料成功");
            // 更新成功，重定向到首页
            return "x_index_home";
        } else {
            log.info("用户修改个人资料失败");
            // 更新失败，返回个人资料页面
            return "x_profile";
        }
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
        return "redirect:/user/toLogin";
    }


}
