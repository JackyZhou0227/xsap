package com.kclm.xsap.web.controller;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.kclm.xsap.config.CustomConfig;
import com.kclm.xsap.consts.OperateType;
import com.kclm.xsap.entity.*;
import com.kclm.xsap.service.*;
import com.kclm.xsap.vo.ClassRecordVo;
import com.kclm.xsap.vo.indexStatistics.IndexPieChartVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author fangkai
 * @description
 * @create 2021-12-07 9:46
 */
@Slf4j
@SpringBootTest
class MemberControllerTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private CourseCardService courseCardService;

    @Autowired
    private MemberCardService memberCardService;


    @Autowired
    private ClassRecordService classRecordService;

    @Autowired
    private ScheduleRecordService scheduleRecordService;

    @Autowired
    private MemberLogService memberLogService;

    @Autowired
    private RechargeRecordService rechargeRecordService;

    @Autowired
    private ConsumeRecordService consumeRecordService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private MemberBindRecordService memberBindRecordService;

    @Autowired
    private MemberCardController memberCardController;

    @Autowired
    private MapCacheService mapCacheService;

    @Autowired
    private CustomConfig customConfig;


    @Test
    void test28() {
        List<ScheduleRecordEntity> allScheduleBeforeToday = scheduleRecordService.list(new QueryWrapper<ScheduleRecordEntity>().select("id", "course_id", "teacher_id", "order_nums", "start_date", "class_time").le("start_date", LocalDate.now()).orderByDesc("start_date"));

        List<ScheduleRecordEntity> scheduleQueryByMonth = allScheduleBeforeToday.stream().filter(schedule -> schedule.getStartDate().getYear() == 2022).collect(Collectors.toList());

        List<Long> teacherIdList = scheduleQueryByMonth.stream().map(ScheduleRecordEntity::getTeacherId).distinct().sorted().collect(Collectors.toList());

        List<String> teacherNameListByIds = employeeService.getTeacherNameListByIds(teacherIdList);
        teacherNameListByIds.forEach(System.out::println);
    }


    @Test
    void test27() {
        LocalDate today = LocalDate.now();
        List<ScheduleRecordEntity> list = scheduleRecordService.list(new QueryWrapper<ScheduleRecordEntity>().select("id", "course_id", "teacher_id", "order_nums", "start_date", "class_time").likeRight("start_date", 2022)/*le("start_date", today).ge()*/);
        list.forEach(System.out::println);
    }

    @Test
    void test26() {
        int countOfAlreadyConfirm = classRecordService.count(new QueryWrapper<ClassRecordEntity>().eq("schedule_id", 90).eq("check_status", 1));
        System.out.println(countOfAlreadyConfirm);

    }

    @Test
    void test25() {
        LocalDate now = LocalDate.now();
        //查询所有最近一个月的排课记录
        List<ScheduleRecordEntity> scheduleFromLastMonth = scheduleRecordService.list(new QueryWrapper<ScheduleRecordEntity>().select("id").le("start_date", now).ge("start_date", now.minusDays(30)));
        //取出最近一个月的所有排课记录的id
        List<Long> scheduleIdList = scheduleFromLastMonth.stream().map(ScheduleRecordEntity::getId).collect(Collectors.toList());


        List<Long> list = classRecordService.list(new QueryWrapper<ClassRecordEntity>().eq("check_status", 1).in("schedule_id", scheduleIdList)).stream().map(ClassRecordEntity::getMemberId).collect(Collectors.toList());

        System.out.println("打印");
        list.forEach(System.out::println);

        System.out.println();
        List<Long> list1 = list.stream().distinct().collect(Collectors.toList());
        list1.forEach(System.out::println);

    }

    @Test
    void test24() {
        Long gap_minute = customConfig.getGap_minute();
        Long cache_time = customConfig.getCache_time();
        System.out.println(cache_time);
        System.out.println(gap_minute);
    }

    @Test
    void test23() throws Exception {
        ExpiryMap<KeyNameOfCache, Object> map = mapCacheService.getCacheInfo();
        map.put(KeyNameOfCache.CACHE_OF_MEMBER_CARD_INFO, "test", 1000);
        System.out.println(map.get(KeyNameOfCache.CACHE_OF_MEMBER_CARD_INFO));
        Thread.sleep(1500);

        System.out.println(map.get(KeyNameOfCache.CACHE_OF_MEMBER_CARD_INFO));


        Thread.sleep(2500);

    }


    @Test
    void test22() {
        memberCardController.deleteOne(44);

    }


    @Test
    void test21() {
        List<MemberCardEntity> cardEntityList = memberCardService.list(new QueryWrapper<MemberCardEntity>().select("id", "name"));
        List<Long> cardIds = cardEntityList.stream().map(MemberCardEntity::getId).collect(Collectors.toList());

        List<IndexPieChartVo> collect = cardEntityList.stream().map(cardEntity -> {
//            List<MemberBindRecordEntity> card_id = memberBindRecordService.list(new QueryWrapper<MemberBindRecordEntity>().eq("card_id", cardEntity.getId()));
            log.debug("\n==>打印ka==>{}", cardEntity);

            int nums = memberBindRecordService.count(new QueryWrapper<MemberBindRecordEntity>().eq("card_id", cardEntity.getId()));
//            HashMap<String, Integer> tempMap = new HashMap<>();
//            tempMap.put(cardEntity.getName(), card_id);

            log.debug("\n==>打印数量==>{}", nums);

            IndexPieChartVo vo = new IndexPieChartVo();
            vo.setName(cardEntity.getName()).setValue(nums);
            return vo;
        }).collect(Collectors.toList());


        collect.forEach(System.out::println);
    }

    @Test
    void test20() {
        List<MemberEntity> currentMonthLogoutMemberInfo = memberService.getCurrentMonthLogoutMemberInfo(LocalDate.now().getYear(), LocalDate.now().getMonthValue());
        currentMonthLogoutMemberInfo.forEach(System.out::println);
    }


    @Test
    void test19() {
        List<MemberEntity> allSurvive = memberService.list(new QueryWrapper<MemberEntity>().likeRight("create_time", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"))).orderByDesc("create_time"));

        allSurvive.forEach(System.out::println);
    }


    @Test
    void test18() {

        try {
            boolean b = courseService.removeById(38);

        } catch (Exception e) {
//            if (e.getCause() == )
            if (e instanceof SQLIntegrityConstraintViolationException) {

            }
            System.out.println(e.getCause());
            log.debug("\n==>e.getCause==>{}", e.getCause());
        }
    }

    @Test
    void test17() {
        List<MemberEntity> list = memberService.getMemberLogOutFromBeginYearToEndYear();
        list.stream().filter(member ->
                member.getLastModifyTime().isBefore(LocalDateTime.of(2021, 1, 1, 0, 0)) && member.getLastModifyTime().isAfter(LocalDateTime.of(2021, 12, 31, 23, 59))
        ).forEach(System.out::println);
        list.forEach(System.out::println);
    }

    @Test
    void test16() {
        List<MemberEntity> memberSurviveList = memberService.list(new QueryWrapper<MemberEntity>().select("create_time").ge("create_time", LocalDateTime.of(2021, 1, 1, 0, 0, 0))
                .le("create_time", LocalDateTime.of(2021, 12, 31, 23, 59, 59)));

        memberSurviveList.forEach(System.out::println);
    }

    @Test
    void test15() {
//        List<MemberEntity> memberLogOutList= memberService.list(new QueryWrapper<MemberEntity>().eq("is_deleted", 1).orderByDesc("last_modify_time"));
        List<MemberEntity> memberLogOutList = memberService.getMemberLogOutInSpecifyYear(2021);

        memberLogOutList.forEach(System.out::println);
    }

    @Test
    void test14() {
        boolean deleteMember = memberService.update(new UpdateWrapper<MemberEntity>().set("is_deleted", 1).set("last_modify_time", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).setSql("version = version + 1").eq("id", 80));
        System.out.println(deleteMember);
    }

    @Test
    void test13() {
        List<ScheduleRecordEntity> scheduleRecordForSpecifyYear = scheduleRecordService.list(new QueryWrapper<ScheduleRecordEntity>()
                .select("id", "course_id", "order_nums", "start_date")
                .ge("start_date", LocalDate.of(2021, 1, 1)).le("start_date", LocalDate.of(2021, 12, 31)).orderByDesc("start_date"));
        scheduleRecordForSpecifyYear.forEach(System.out::println);
    }

    @Test
    void test12() {
        List<ScheduleRecordEntity> classAlreadyTaken = scheduleRecordService.list(new QueryWrapper<ScheduleRecordEntity>()
                .select("course_id", "teacher_id", "order_nums")
                .le("start_date", LocalDate.now())
                .le("class_time", LocalTime.now())
                .eq("teacher_id", 1)
                .orderByDesc("start_date"));

        classAlreadyTaken.forEach(System.out::println);
    }


    @Test
    void test11() {
        List<ScheduleRecordEntity> list = scheduleRecordService.list(new QueryWrapper<ScheduleRecordEntity>()/*.select("id","course_id", "teacher_id", "order_nums")*/.le("start_date", LocalDate.now()).le("class_time", LocalTime.now()).groupBy("teacher_id"));
        List<Long> tIds = employeeService.list().stream().map(EmployeeEntity::getId).collect(Collectors.toList());
        List<ScheduleRecordEntity> teacher_id = scheduleRecordService.list(new QueryWrapper<ScheduleRecordEntity>().in("teacher_id", tIds));
        teacher_id.forEach(System.out::println);

    }


    @Test
    void test10() {
        LocalDateTime beginTime = LocalDateTime.parse(2019 + "-01-01 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endTime = LocalDateTime.parse(2022 + "-12-31 23:59:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        List<ConsumeRecordEntity> consumeInfoWithTimeAndMoneyForYear = consumeRecordService.list(new QueryWrapper<ConsumeRecordEntity>()
                .select("money_cost", "create_time").ge("create_time", beginTime).le("create_time", endTime).orderByDesc("create_time"));
        consumeInfoWithTimeAndMoneyForYear.forEach(System.out::println);
    }

    @Test
    void test09() {
        List<ConsumeRecordEntity> consumeByYear = consumeRecordService.list(new QueryWrapper<ConsumeRecordEntity>().likeRight("create_time", 2021).orderByDesc("create_time").select("money_cost", "create_time"));
        System.out.println(consumeByYear.get(0));
        System.out.println(consumeByYear);
        int s = consumeByYear.stream().map(ConsumeRecordEntity::getCreateTime).max((o1, o2) -> {
            boolean after = o1.isAfter(o2);
            if (after) {
                return 1;
            } else {
                return -1;
            }
        }).get().getMonth().getValue();
        System.out.println(s);
    }


    @Test
    @Transactional
    void test08() {
        MemberCardEntity cardEntity = memberCardService.getById(1);


        MemberLogEntity memberLogEntity = new MemberLogEntity()
                .setType(OperateType.BINDING_CARD_OPERATION.getMsg())
                .setInvolveMoney(cardEntity.getPrice())
                .setOperator("operator")
                .setMemberBindId(10L)
                .setCreateTime(LocalDateTime.now());
        log.debug("\n==>oldLog==>{}", memberLogEntity);
        boolean isSaveLog = memberLogService.save(memberLogEntity);
        log.debug("\n==>添加操作记录是否成功==>{}", isSaveLog);
        if (!isSaveLog) {
            log.debug("添加操作记录失败！");
//            return R.error("会员绑定失败！" );
        }

        log.debug("\n==>memLOG==>{}", memberLogEntity);
        RechargeRecordEntity rechargeRecordEntity = new RechargeRecordEntity()
                .setAddCount(5)
                .setAddDay(7)
                .setReceivedMoney(BigDecimal.valueOf(88))
                .setPayMode("微信测试")
                .setMemberBindId(10L)
                .setLogId(memberLogEntity.getId());

        boolean save = rechargeRecordService.save(rechargeRecordEntity);
        log.debug("save______{}", save);

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    void test06() {
        List<ClassRecordVo> classRecordList = classRecordService.getClassRecordList(28L);
        for (ClassRecordVo classRecordVo : classRecordList) {
            System.out.println(classRecordVo);
        }
    }

    @Test
    void test07() {
        List<ScheduleRecordEntity> sameDateSchedule = scheduleRecordService.getSameDateSchedule(LocalDate.now());
        for (ScheduleRecordEntity entity : sameDateSchedule) {
            CourseEntity courseEntity = entity.getCourseEntity();
            System.out.println(courseEntity.toString());
            System.out.println(entity);
        }
    }

    @Test
    void test03() {
        ArrayList<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(2L);
        list.add(15L);
        list.add(17L);
        list.add(23L);
        MemberCardEntity byId = memberCardService.getById(list);

        log.debug("\n==>mem==>{}", byId);
    }


    @Test
    public void test01() {
        List<CourseCardEntity> courseCardEntityListByCardId = courseCardService.list(new QueryWrapper<CourseCardEntity>().eq("card_id", 12));
        List<Long> courseId = courseCardEntityListByCardId.stream().map(CourseCardEntity::getCourseId).sorted(Long::compareTo).collect(Collectors.toList());
        System.out.println(courseCardEntityListByCardId);
        System.out.println(courseId);
        System.out.println(courseId.isEmpty());

    }


    /**
     * 测试得出 mp生成的save方法回自动将新插入的元素的id映射到entity上
     */
    @Test
    void memberAdd() {
        MemberEntity entity = new MemberEntity().setSex("女").setName("tom").setNote("testNote").setVersion(2).setPhone("242142323");
        System.out.println(memberService);
        boolean save = memberService.save(entity);
        System.out.println(entity);

    }

    @Test
    void testDate() {
//        LocalDateTime now = LocalDateTime.now();
//        System.out.println(now);

        DateTime now1 = DateTime.now();
        System.out.println(now1);

        DateTime dateTime = now1.offsetNew(DateField.DAY_OF_YEAR, -30);
        System.out.println(dateTime + "jj");

        DateTime offset = now1.offset(DateField.DAY_OF_WEEK, -30);
        System.out.println(offset);
        System.out.println(now1);
    }
}
