package com.kclm.xsap.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kclm.xsap.model.entity.*;
import com.kclm.xsap.model.vo.IndexHomeDateVo;
import com.kclm.xsap.model.vo.indexStatistics.IndexAddAndStreamInfoVo;
import com.kclm.xsap.model.vo.indexStatistics.IndexPieChartVo;
import com.kclm.xsap.service.*;
import com.kclm.xsap.utils.R;
import com.kclm.xsap.utils.time.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("/index")
public class IndexController {

    @Resource
    private EmployeeService employeeService;

    @Resource
    private MemberCardService memberCardService;

    @Resource
    private MemberBindRecordService memberBindRecordService;

    @Resource
    private MemberService memberService;

    @Resource
    private ReservationRecordService reservationRecordService;

    @Resource
    private RechargeRecordService rechargeRecordService;

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

    @PostMapping("/modifyPwd.do")
    public String modifyPwd(@RequestParam("id") Long id, @RequestParam("oldPwd") String oldPwd, @RequestParam("newPwd") String newPwd, @RequestParam("pwd2") String pwd2, Model model) {
        if (id == null || oldPwd == null || newPwd == null || pwd2 == null) {
            model.addAttribute("CHECK_PWD_ERROR", 0);
            return "x_modify_password";
        }
        EmployeeEntity employeeEntity = employeeService.getById(id);
        if (!oldPwd.equals(employeeEntity.getRolePassword())) {
            model.addAttribute("CHECK_PWD_ERROR", 1);
            return "x_modify_password";
        }
        if (!newPwd.equals(pwd2)) {
            model.addAttribute("CHECK_PWD_ERROR", 2);
            return "x_modify_password";
        }
        employeeEntity.setRolePassword(newPwd);
        employeeService.updateById(employeeEntity);

        return "redirect:/index/logout";
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
     * @param model          用于在视图和控制器之间传递数据
     * @return 返回视图的名称，根据修改结果决定是重定向到个人资料页面还是首页
     */
    @PostMapping("/modifyUser.do")
    public String modifyUser(EmployeeEntity employeeEntity, Model model) {
        log.info("修改用户信息");
        // 根据ID获取原始用户信息
        EmployeeEntity originMessage = employeeService.getEmployeeByRoleName(employeeEntity.getRoleName());
        //用于回显的三个参数
        employeeEntity.setAvatarUrl(originMessage.getAvatarUrl());
        employeeEntity.setId(originMessage.getId());
        employeeEntity.setCreateTime(originMessage.getCreateTime());
        // 如果新手机号已存在且与原手机号不同，则更新失败
        if (employeeService.isPhoneExists(employeeEntity.getPhone()) && !originMessage.getPhone().equals(employeeEntity.getPhone())) {
            log.info("手机号已存在");
            model.addAttribute("CHECK_PHONE_EXIST", true);

            // 返回个人资料页面，提示手机已存在
            model.addAttribute("userInfo", employeeEntity);
            return "x_profile";
        }

        // 设置最后修改时间
        employeeEntity.setLastModifyTime(LocalDateTime.now());
        // 更新用户信息
        if (employeeService.updateById(employeeEntity)) {
            log.info("用户修改个人资料成功");
            // 更新成功，重定向到首页
            model.addAttribute("userInfo", employeeEntity);
            return "x_profile";
        } else {
            log.error("用户修改个人资料失败");
            // 更新失败，返回个人资料页面
            model.addAttribute("userInfo", employeeEntity);
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


    @PostMapping("/homePageInfo/statisticsOfMemberCard.do")
    public ResponseEntity<Map<String, Object>> statisticsOfMemberCard() {

        Map<String, Object> returnData = new HashMap<>();

        List<MemberCardEntity> cardEntityList = memberCardService.list(null);
        log.debug("所有会员卡{}", cardEntityList);
        if (cardEntityList.isEmpty()) {

            returnData.put("msg", "还没有创建会员卡");
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        }
        List<IndexPieChartVo> indexPieChartVos = new ArrayList<>();
        for (MemberCardEntity memberCardEntity : cardEntityList) {
            IndexPieChartVo indexPieChartVo = new IndexPieChartVo();
            int count = memberBindRecordService.count(new QueryWrapper<MemberBindRecordEntity>().eq("card_id", memberCardEntity.getId()));
            indexPieChartVo.setName(memberCardEntity.getName());
            indexPieChartVo.setValue(count);
            indexPieChartVos.add(indexPieChartVo);
        }
        returnData.put("code",0);
        returnData.put("data", indexPieChartVos);
        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }

    @GetMapping("/homePageInfo/statisticsOfNewAndLostPeople.do")
    public ResponseEntity<Map<String, Object>> newAndLostPeople() {

        Map<String, Object> returnData = new HashMap<>();

        int day = LocalDate.now().getDayOfMonth();
//        int year = LocalDate.now().getYear();
//        int month = LocalDate.now().getMonthValue();
        IndexAddAndStreamInfoVo infoVo = new IndexAddAndStreamInfoVo();
        //获取天数
        List<String> time = new ArrayList<>();
        for (int i = 1; i <= day; i++) {
            time.add(String.valueOf(i));
        }
        //查找所有成员
//        List<MemberEntity> memberEntityList = memberService.list(new QueryWrapper<MemberEntity>()
//                .select("create_time")
//        );
        List<MemberEntity> memberEntityList = memberService.list();
        List<LocalDateTime> createTimeList = new ArrayList<>();
        //得到成员的创建时间
        for (MemberEntity memberEntity : memberEntityList) {
            createTimeList.add(memberEntity.getCreateTime());
        }
        List<Integer> data = Data.Data(createTimeList, time);
        //查找所有流失成员的日期
        List<LocalDateTime> delTimeList = memberService.delTimelist();
        List<Integer> data2 = Data.Data(delTimeList, time);
        infoVo.setXname("/日");
        infoVo.setTime(time);
        infoVo.setData(data);
        infoVo.setData2(data2);

        returnData.put("code",0);
        returnData.put("data", infoVo);
        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }

    @GetMapping("/homePageInfo/statisticsOfDailyCharge.do")
    public ResponseEntity<Map<String, Object>> dailyCharge() {
        Map<String, Object> returnData = new HashMap<>();
        int day = LocalDate.now().getDayOfMonth();
//        int year = LocalDate.now().getYear();
//        int month = LocalDate.now().getMonthValue();
        //查询当前月的所有充值记录
        List<RechargeRecordEntity> rechargeRecordEntityList = rechargeRecordService.list(new QueryWrapper<RechargeRecordEntity>()
                .select("received_money", "create_time")
                .likeRight("create_time", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM")))
        );
//        log.info("当月所有充值记录:"+rechargeRecordEntityList);
        HashMap<Integer, Integer> moneyAndDateList = new HashMap<>();
        for (RechargeRecordEntity rechargeRecordEntity : rechargeRecordEntityList) {
            //取出每次收费的金额
            int rechargeOfOnce = rechargeRecordEntity.getReceivedMoney().intValue();
            //取出每次消费的日期的日
            int dayOfMonth = rechargeRecordEntity.getCreateTime().getDayOfMonth();
            moneyAndDateList.put(dayOfMonth, moneyAndDateList.getOrDefault(dayOfMonth, 0) + rechargeOfOnce);
        }
        log.info("当月所有充值记录(钱和日期):" + moneyAndDateList);
        log.info("当月所有充值记录(钱和日期)个数:" + moneyAndDateList.size());
        List<String> time = new ArrayList<>();
        List<Integer> data = new ArrayList<>();
        for (int i = 1; i <= day; i++) {
            time.add(String.valueOf(i));
            data.add(moneyAndDateList.getOrDefault(i, 0));
        }
        IndexAddAndStreamInfoVo infoVo = new IndexAddAndStreamInfoVo();
        infoVo.setTitle("当月每日收费统计");
        infoVo.setXname("日");
        infoVo.setTime(time);
        infoVo.setData(data);
        returnData.put("code",0);
        returnData.put("data", infoVo);
        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }

    @PostMapping("/homePageInfo.do")
    public ResponseEntity<Map<String, Object>> homePageInfo() {
        Map<String, Object> returnData = new HashMap<>();
        IndexHomeDateVo indexHomeDateVo = new IndexHomeDateVo();
        //会员总数
        int countMembers = memberService.count(null);
        indexHomeDateVo.setTotalMembers(countMembers);
        //活跃用户
        List<ReservationRecordEntity> reservationRecordEntityList = reservationRecordService.list(new QueryWrapper<ReservationRecordEntity>().select("member_id")
                .le("create_time", LocalDateTime.now())
                .ge("create_time", LocalDateTime.now().minusDays(30)));
//        reservationRecordEntityList.forEach(System.out::println);
        Set<Long> ids = new TreeSet<>();
        for (ReservationRecordEntity reservationRecordEntity : reservationRecordEntityList) {
            ids.add(reservationRecordEntity.getMemberId());
        }
        indexHomeDateVo.setActiveMembers(ids.size());
        //预约总数
        int countActive = reservationRecordService.count(new QueryWrapper<ReservationRecordEntity>().eq("status", 1));
        indexHomeDateVo.setTotalReservations(countActive);
        returnData.put("code",0);
        returnData.put("data", indexHomeDateVo);
        return new ResponseEntity<>(returnData, HttpStatus.OK);
    }

}
