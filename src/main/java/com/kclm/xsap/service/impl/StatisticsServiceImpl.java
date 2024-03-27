package com.kclm.xsap.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kclm.xsap.mapper.*;
import com.kclm.xsap.model.entity.ConsumeRecordEntity;
import com.kclm.xsap.model.entity.CourseEntity;
import com.kclm.xsap.model.entity.MemberEntity;
import com.kclm.xsap.model.entity.ScheduleRecordEntity;
import com.kclm.xsap.model.vo.MemberCardStatisticsVo;
import com.kclm.xsap.model.vo.MemberCardStatisticsWithTotalDataInfoVo;
import com.kclm.xsap.model.vo.statistics.CardCostVo;
import com.kclm.xsap.model.vo.statistics.ClassCostVo;
import com.kclm.xsap.model.vo.statistics.StatisticsOfCardCostVo;
import com.kclm.xsap.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Resource
    private ConsumeRecordMapper consumeRecordMapper;


    @Resource
    private MemberMapper memberMapper;


    @Resource
    private MemberBindRecordMapper memberBindRecordMapper;

    @Resource
    private MemberService memberService;

    @Resource
    private MemberLogService memberLogService;

    @Resource
    private ScheduleRecordMapper scheduleRecordMapper;

    @Resource
    private EmployeeService employeeService;

    @Override
    public MemberCardStatisticsWithTotalDataInfoVo getCardInfoStatistics() {

        List<MemberCardStatisticsVo> memberCardStatisticsVoList = memberMapper.getMemberCardStatistics();

        //总课时
        int totalCourseTimeAll = 0;
        //已用课时
        int usedCourseTimeAll = 0;
        //剩余课时
        int remainCourseTimeAll = 0;
        //总金额
        BigDecimal totalMoneyAll = new BigDecimal("0.00");
        //已用金额
        BigDecimal usedMoneyAll = new BigDecimal("0.00");
        //剩余金额
        BigDecimal remainMoneyAll = new BigDecimal("0.00");
        for (MemberCardStatisticsVo memberCardStatisticsVo : memberCardStatisticsVoList) {

            totalCourseTimeAll += memberCardStatisticsVo.getTotalClassTimes();
            ;

            usedCourseTimeAll += memberCardStatisticsVo.getUsedClassTimes();

            remainCourseTimeAll += memberCardStatisticsVo.getRemainingClassTimes();

            totalMoneyAll = totalMoneyAll.add(memberCardStatisticsVo.getLumpSumBigD());

            usedMoneyAll = usedMoneyAll.add(memberCardStatisticsVo.getAmountUsedBigD());

            remainMoneyAll = remainMoneyAll.add(memberCardStatisticsVo.getBalanceBigD());
        }

        MemberCardStatisticsWithTotalDataInfoVo memberCardStatisticsWithTotalDataInfoVo = new MemberCardStatisticsWithTotalDataInfoVo();
        memberCardStatisticsWithTotalDataInfoVo.setMemberCardStatisticsVos(memberCardStatisticsVoList)
                .setTotalCourseTimeAll(totalCourseTimeAll)
                .setUsedCourseTimeAll(usedCourseTimeAll)
                .setRemainCourseTimeAll(remainCourseTimeAll)
                .setTotalMoneyAll(totalMoneyAll)
                .setUsedMoneyAll(usedMoneyAll)
                .setRemainMoneyAll(remainMoneyAll);

        return memberCardStatisticsWithTotalDataInfoVo;
    }

    @Override
    public CardCostVo getcardCostVo(StatisticsOfCardCostVo statisticsOfCardCostVo) {
        Integer unit = statisticsOfCardCostVo.getUnit();
        Integer yearOfSelect = statisticsOfCardCostVo.getYearOfSelect();
        Integer beginYear = statisticsOfCardCostVo.getBeginYear();
        Integer endYear = statisticsOfCardCostVo.getEndYear();
        List<ConsumeRecordEntity> consumeRecordEntityList = consumeRecordMapper.selectList(new QueryWrapper<ConsumeRecordEntity>()
                .select("money_cost", "create_time")
                .orderByDesc("create_time"));
//        log.info("消费记录" + consumeRecordEntityList);

        //月和季度消费全记录
        List<ConsumeRecordEntity> List = new ArrayList<>();
        if (unit != 3) {
            for (ConsumeRecordEntity consumeRecordEntity : consumeRecordEntityList) {
                if (consumeRecordEntity.getCreateTime().getYear() == yearOfSelect) {
                    List.add(consumeRecordEntity);
                }
            }
            if (List.isEmpty()) {
                return null;
            }
        }
//        log.info("月和季度消费全记录" + List);
        //月份消费
        HashMap<Integer, Integer> monthCostMap = new HashMap<>();
        //季度消费
        HashMap<Integer, Integer> quarterCostMap = new HashMap<>();
        for (ConsumeRecordEntity consumeRecordEntity : List) {
            int month = consumeRecordEntity.getCreateTime().getMonthValue();
            monthCostMap.put(month, monthCostMap.getOrDefault(month, 0) + consumeRecordEntity.getMoneyCost().intValue());

            //根据月份得出季度
            int quarter = (month - 1) / 3 + 1;
            quarterCostMap.put(quarter, quarterCostMap.getOrDefault(quarter, 0) + consumeRecordEntity.getMoneyCost().intValue());
        }
        String title = null;
        String xName = null;
        List<String> time = new ArrayList<>();
        List<Integer> data = new ArrayList<>();
        CardCostVo cardCostVo = new CardCostVo();
        if (unit == 1) {
            title = "月收费模式";
            xName = "月";
            timeAndDataCount(yearOfSelect, monthCostMap, time, data);
        } else if (unit == 2) {
            title = "季度收费模式";
            xName = "季度";
            quarterAndDataCount(yearOfSelect, quarterCostMap, time, data);
        } else if (unit == 3) {
            title = "年收费模式";
            xName = "年";
            //年消费记录
            //yearAndDataCount(Collections.singletonList(consumeRecordEntityList), time, data);
            List<ConsumeRecordEntity> yearList = new ArrayList<>();
            for (ConsumeRecordEntity consumeRecordEntity : consumeRecordEntityList) {
                if (consumeRecordEntity.getCreateTime().getYear() >= beginYear
                        && consumeRecordEntity.getCreateTime().getYear() <= endYear) {
                    yearList.add(consumeRecordEntity);
                }
            }
            if (yearList.isEmpty()) {
                return null;
            }
            HashMap<Integer, Integer> yearCostMap = new HashMap<>();
            for (ConsumeRecordEntity recordEntity : yearList) {
                int year = recordEntity.getCreateTime().getYear();
                yearCostMap.put(year, yearCostMap.getOrDefault(year, 0) + recordEntity.getMoneyCost().intValue());
            }
            for (int i = beginYear; i <= endYear; i++) {
                Integer yearCost = yearCostMap.getOrDefault(i, 0);
                time.add(i + "年");
                data.add(yearCost);
            }
        }
        cardCostVo.setTitle(title).setXname(xName)
                .setTime(time)
                .setData(data);
        log.info(String.valueOf(cardCostVo));
        return cardCostVo;
    }


    //课消统计
    @Override
    public ClassCostVo getclassCostVo(StatisticsOfCardCostVo statisticsOfCardCostVo) {
        Integer unit = statisticsOfCardCostVo.getUnit();
        Integer yearOfSelect = statisticsOfCardCostVo.getYearOfSelect();
        Integer beginYear = statisticsOfCardCostVo.getBeginYear();
        Integer endYear = statisticsOfCardCostVo.getEndYear();
        ClassCostVo classCostVo = new ClassCostVo();
        LocalDate today = LocalDate.now();

        //获取所有今天之前的排课记录
        List<ScheduleRecordEntity> allScheduleBeforeToday = scheduleRecordMapper.selectList(new QueryWrapper<ScheduleRecordEntity>()
                .select("id", "course_id", "teacher_id", "order_nums", "start_date")
                .le("start_date", today)
                .orderByDesc("start_date"));
        List<String> tname;
        String title = "";
        List<String> time = new ArrayList<>();
        ArrayList<List<Integer>> data = new ArrayList<>();
        ArrayList<List<Float>> data2 = new ArrayList<>();
        //老师id，月份，课次
        HashMap<Long, HashMap<Integer, Integer>> idTimeCount = new HashMap<>();
        //老师id，月份，钱
        HashMap<Long, HashMap<Integer, Float>> idTimeMoney = new HashMap<>();
        //指定年份的排课记录
        List<ScheduleRecordEntity> scheduleByYear = new ArrayList<>();
        if (unit != 3) {
            for (ScheduleRecordEntity scheduleRecordEntity : allScheduleBeforeToday) {
                if (scheduleRecordEntity.getStartDate().getYear() == yearOfSelect) {
                    scheduleByYear.add(scheduleRecordEntity);
                }
            }
            if (scheduleByYear.isEmpty()) {
                return null;
            }
        }
        //获取所有老师的id
        List<Long> teacherIdList = new ArrayList<>();
        for (ScheduleRecordEntity scheduleRecordEntity : scheduleByYear) {
            teacherIdList.add(scheduleRecordEntity.getTeacherId());
        }
        log.info("获取所有老师的id" + teacherIdList.size());
        log.info("id和次数" + idTimeCount);
        log.info("id和钱" + idTimeMoney);
        int flag = 0;
        int i = 1;

        List<ConsumeRecordEntity> consumeRecordEntities = consumeRecordMapper.selectList(new QueryWrapper<ConsumeRecordEntity>().select());
        //移除scheduleId为null的记录
        consumeRecordEntities.removeIf(consumeRecordEntity -> consumeRecordEntity.getScheduleId() == null);
        if (unit == 1) {
            title = "老师课时月消费统计";
            for (Long id : teacherIdList) {
                //存放老师时间和次数(月)
                HashMap<Integer, Integer> timeAndCount = new HashMap<>();
                //存放老师时间和金额(月)
                HashMap<Integer, Float> timeAndMoney = new HashMap<>();
                for (ScheduleRecordEntity scheduleRecordEntity : scheduleByYear) {
                    if (scheduleRecordEntity.getTeacherId().equals(id)) {
                        BigDecimal moneyCount = new BigDecimal(0);
                        int totalcount = 0;
                        if (!consumeRecordEntities.isEmpty()) {
                            for (ConsumeRecordEntity consumeRecordEntity : consumeRecordEntities) {
                                if (consumeRecordEntity.getScheduleId().equals(scheduleRecordEntity.getId())) {
                                    moneyCount = moneyCount.add(consumeRecordEntity.getMoneyCost());
                                    totalcount = totalcount + consumeRecordEntity.getCardCountChange();
                                }

                            }
                        }

                        //获取该排课上课日期的月份
                        int monthValue = scheduleRecordEntity.getStartDate().getMonthValue();
                        //老师在同一个月份里的次数和
                        timeAndCount.put(monthValue, timeAndCount.getOrDefault(monthValue, 0) + totalcount);
                        //老师在同一月里的金额和
                        timeAndMoney.put(monthValue, timeAndMoney.getOrDefault(monthValue, 0F) + moneyCount.floatValue());
                    }
                }
                idTimeCount.put(id, timeAndCount);
                idTimeMoney.put(id, timeAndMoney);
            }
            if (yearOfSelect == LocalDate.now().getYear()) {
                flag = LocalDate.now().getMonthValue();
            } else {
                flag = 12;
            }
            for (int t = 1; t <= flag; t++) {
                time.add(t + "月");
            }
            countAndMoney(idTimeCount, idTimeMoney, i, flag, data, data2);
            tname = employeeService.getTeacherNameListByIds(teacherIdList);
        } else if (unit == 2) {
            log.info("传进来的值" + statisticsOfCardCostVo);
            title = "老师课时季消费统计";
            if (yearOfSelect == LocalDate.now().getYear()) {
                flag = (LocalDate.now().getMonthValue() - 1) / 3 + 1;
            } else {
                flag = 4;
            }
            for (Long id : teacherIdList) {
                //存放老师时间和次数(季度)
                HashMap<Integer, Integer> quarterAndCount = new HashMap<>();
                //存放老师时间和金额(季度)
                HashMap<Integer, Float> quarterAndMoney = new HashMap<>();
                for (ScheduleRecordEntity scheduleRecordEntity : scheduleByYear) {
                    if (scheduleRecordEntity.getTeacherId().equals(id)) {
                        BigDecimal moneyCount = new BigDecimal(0);
                        int totalcount = 0;
                        if (!consumeRecordEntities.isEmpty()) {
                            for (ConsumeRecordEntity consumeRecordEntity : consumeRecordEntities) {
                                if (consumeRecordEntity.getScheduleId().equals(scheduleRecordEntity.getId())) {
                                    moneyCount = moneyCount.add(consumeRecordEntity.getMoneyCost());
                                    totalcount = totalcount + consumeRecordEntity.getCardCountChange();
                                }

                            }
                        }

                        //获取该排课上课日期的月份
                        int monthValue = scheduleRecordEntity.getStartDate().getMonthValue();
                        int quarter = (monthValue - 1) / 3 + 1;
                        //老师在同一个季度里的次数和
                        quarterAndCount.put(quarter, quarterAndCount.getOrDefault(quarter, 0) + totalcount);
                        //老师在同一个季度里的金额和
                        quarterAndMoney.put(quarter, quarterAndMoney.getOrDefault(quarter, 0F) + moneyCount.floatValue());
                    }
                }
                idTimeCount.put(id, quarterAndCount);
                idTimeMoney.put(id, quarterAndMoney);
            }
            countAndMoney(idTimeCount, idTimeMoney, i, flag, data, data2);
            tname = employeeService.getTeacherNameListByIds(teacherIdList);
            for (int t = 1; t <= flag; t++) {
                time.add("第" + t + "季度");
            }
        } else {
            title = "老师课时年消费统计";
            List<ScheduleRecordEntity> scheduleBystartAndEnd = new ArrayList<>();
            //获取所有老师的id
            for (ScheduleRecordEntity scheduleRecordEntity : allScheduleBeforeToday) {
                if (scheduleRecordEntity.getStartDate().getYear() >= beginYear && scheduleRecordEntity.getStartDate().getYear() <= endYear) {
                    scheduleBystartAndEnd.add(scheduleRecordEntity);
                }
            }
            log.info("统计年课程记录：" + scheduleBystartAndEnd);
            if (scheduleBystartAndEnd.isEmpty()) {
                return null;
            }
            List<Long> tIdList = new ArrayList<>();
            for (ScheduleRecordEntity recordEntity : scheduleBystartAndEnd) {
                tIdList.add(recordEntity.getTeacherId());
            }
            for (Long id : tIdList) {
                //存放老师时间和次数(年)
                HashMap<Integer, Integer> yearAndCount = new HashMap<>();
                //存放老师时间和金额(季度)
                HashMap<Integer, Float> yearAndMoney = new HashMap<>();
                for (ScheduleRecordEntity recordEntity : scheduleBystartAndEnd) {
                    if (recordEntity.getTeacherId().equals(id)) {
                        BigDecimal moneyCount = new BigDecimal(0);
                        int totalcount = 0;
                        if (!consumeRecordEntities.isEmpty()) {
                            for (ConsumeRecordEntity consumeRecordEntity : consumeRecordEntities) {
                                if (consumeRecordEntity.getScheduleId().equals(recordEntity.getId())) {
                                    moneyCount = moneyCount.add(consumeRecordEntity.getMoneyCost());
                                    totalcount = totalcount + consumeRecordEntity.getCardCountChange();
                                }

                            }
                        }
                        //获取该排课上课日期的月份
                        int yearValue = recordEntity.getStartDate().getYear();
                        //老师在同一个季度里的次数和
                        yearAndCount.put(yearValue, yearAndCount.getOrDefault(yearValue, 0) + totalcount);
                        //老师在同一个季度里的金额和
                        yearAndMoney.put(yearValue, yearAndMoney.getOrDefault(yearValue, 0F) + moneyCount.floatValue());
                    }
                }
                idTimeCount.put(id, yearAndCount);
                idTimeMoney.put(id, yearAndMoney);
            }
            i = beginYear;
            flag = endYear;
            countAndMoney(idTimeCount, idTimeMoney, i, flag, data, data2);
            tname = employeeService.getTeacherNameListByIds(tIdList);
            for (int a = beginYear; a <= endYear; a++) {
                time.add(a + "年");
            }

        }
        classCostVo.setTitle(title)
                .setTname(tname)
                .setTime(time)
                .setData(data)
                .setData2(data2);
        return classCostVo;
    }

    private void countAndMoney(HashMap<Long, HashMap<Integer, Integer>> idTimeCount, HashMap<Long, HashMap<Integer, Float>> idTimeMoney, int i, int flag, ArrayList<List<Integer>> data, ArrayList<List<Float>> data2) {
        for (Map.Entry<Long, HashMap<Integer, Integer>> entry : idTimeCount.entrySet()) {
            HashMap<Integer, Integer> integerIntegerHashMap = idTimeCount.get(entry.getKey());
            ArrayList<Integer> classCosts = new ArrayList<>();
            for (int a = i; a <= flag; a++) {
                Integer count = integerIntegerHashMap.get(a);
                classCosts.add(count);
            }
            data.add(classCosts);
        }
        for (Map.Entry<Long, HashMap<Integer, Float>> entry : idTimeMoney.entrySet()) {
            HashMap<Integer, Float> monthAndMoney = idTimeMoney.get(entry.getKey());
            ArrayList<Float> moneys = new ArrayList<>();
            for (int a = i; a <= flag; a++) {
                Float money = monthAndMoney.get(a);
                moneys.add(money);
            }
            data2.add(moneys);
        }
    }


    @Override
    public CardCostVo getCountCostVo(StatisticsOfCardCostVo statisticsOfCardCostVo) {
        Integer unit = statisticsOfCardCostVo.getUnit();
        Integer yearOfSelect = statisticsOfCardCostVo.getYearOfSelect();
        Integer beginYear = statisticsOfCardCostVo.getBeginYear();
        Integer endYear = statisticsOfCardCostVo.getEndYear();
        CardCostVo cardCostVo = new CardCostVo();
        //指定年份的排课记录
        List<ScheduleRecordEntity> scheduleRecordEntityList = new ArrayList<>();
        String title;
        //x轴名字
        String xname;
        //x轴数据
        List<String> time = new ArrayList<>();
        //y轴数据
        List<Integer> data = new ArrayList<>();
        //获取所有排课记录
        List<ScheduleRecordEntity> allSchedule = scheduleRecordMapper.selectList(new QueryWrapper<ScheduleRecordEntity>()
                .select("id", "course_id", "teacher_id", "order_nums", "start_date")
                .orderByDesc("start_date"));
        if (unit != 3) {
            for (ScheduleRecordEntity scheduleRecordEntity : allSchedule) {
                if (scheduleRecordEntity.getStartDate().getYear() == yearOfSelect) {
                    scheduleRecordEntityList.add(scheduleRecordEntity);
                }
            }
            if (scheduleRecordEntityList.isEmpty()) {
                return null;
            }
        }
        log.info("查询的个数" + scheduleRecordEntityList.size());
        //月份次数
        HashMap<Integer, Integer> timeAndCount = new HashMap<>();
        //季度次数
        HashMap<Integer, Integer> quarterAndCount = new HashMap<>();
        for (ScheduleRecordEntity scheduleRecordEntity : scheduleRecordEntityList) {
            int month = scheduleRecordEntity.getStartDate().getMonthValue();
            timeAndCount.put(month, timeAndCount.getOrDefault(month, 0) + 1);
            int quarter = (month - 1) / 3 + 1;
            quarterAndCount.put(quarter, quarterAndCount.getOrDefault(quarter, 0) + 1);
        }

        if (unit == 1) {
            title = "月课时统计";
            xname = "月";
            timeAndDataCount(yearOfSelect, timeAndCount, time, data);
        } else if (unit == 2) {
            title = "季度课时统计";
            xname = "季度";
            quarterAndDataCount(yearOfSelect, quarterAndCount, time, data);
        } else {
            title = "年课时统计";
            xname = "年";
            List<ScheduleRecordEntity> yearList = new ArrayList<>();
            for (ScheduleRecordEntity scheduleRecordEntity : allSchedule) {
                if (scheduleRecordEntity.getStartDate().getYear() >= beginYear
                        && scheduleRecordEntity.getStartDate().getYear() <= endYear) {
                    yearList.add(scheduleRecordEntity);
                }
            }
            if (yearList.isEmpty()) {
                return null;
            }
            HashMap<Integer, Integer> yearCostMap = new HashMap<>();
            for (ScheduleRecordEntity scheduleRecordEntity : yearList) {
                int year = scheduleRecordEntity.getStartDate().getYear();
                yearCostMap.put(year, yearCostMap.getOrDefault(year, 0) + 1);
            }
            for (int i = beginYear; i <= endYear; i++) {
                Integer yearCost = yearCostMap.getOrDefault(i, 0);
                time.add(i + "年");
                data.add(yearCost);
            }
        }
        cardCostVo.setTitle(title)
                .setXname(xname)
                .setData(data)
                .setTime(time);
        return cardCostVo;
    }

    @Override
    public CardCostVo getCountNum(StatisticsOfCardCostVo statisticsOfCardCostVo) {
        Integer unit = statisticsOfCardCostVo.getUnit();
        Integer yearOfSelect = statisticsOfCardCostVo.getYearOfSelect();
        Integer beginYear = statisticsOfCardCostVo.getBeginYear();
        Integer endYear = statisticsOfCardCostVo.getEndYear();
        String title;
        //x轴名字
        String xname;
        //x轴数据
        List<String> time = new ArrayList<>();
        //y轴数据
        List<Integer> data = new ArrayList<>();
        List<Integer> data2 = new ArrayList<>();

        List<MemberEntity> memberEntityList = memberService.list(new QueryWrapper<MemberEntity>()
                .select("create_time")
        );
        //得到成员的创建时间
        List<LocalDateTime> createTimeList = new ArrayList<>();
        for (MemberEntity memberEntity : memberEntityList) {
            createTimeList.add(memberEntity.getCreateTime());
        }
        //得到流失成员的创建时间
        List<LocalDateTime> delTimeList = memberService.delTimelist();
        //月份和当月新增会员数量
        HashMap<Integer, Integer> monthAndAdd = new HashMap<>();
        //月份和当月注销会员数量
        HashMap<Integer, Integer> monthAndDel = new HashMap<>();
        //季度和新增会员数量
        HashMap<Integer, Integer> quarterAndAdd = new HashMap<>();
        //季度和注销会员数量
        HashMap<Integer, Integer> quarterAndDel = new HashMap<>();
        log.info("增加成员集合" + createTimeList);
        log.info("流失成员集合" + delTimeList);
        log.info("流失月份" + monthAndDel);
        int flag;
        if (unit == 1) {
            title = "月新增与流失客户数量统计";
            xname = "月";
            if (yearOfSelect == LocalDate.now().getYear()) {
                flag = LocalDate.now().getMonthValue();
            } else {
                flag = 12;
            }
            totalcount(createTimeList, yearOfSelect, monthAndAdd, data, flag, unit);
            totalcount(delTimeList, yearOfSelect, monthAndDel, data2, flag, unit);
            for (int i = 1; i <= flag; i++) {
                time.add(i + "月");
            }
        } else if (unit == 2) {
            title = "季度新增与流失客户数量统计";
            xname = "季度";
            if (yearOfSelect == LocalDate.now().getYear()) {
                flag = (LocalDate.now().getMonthValue() - 1) / 3 + 1;
            } else {
                flag = 4;
            }
            totalcount(createTimeList, yearOfSelect, quarterAndAdd, data, flag, unit);
            totalcount(delTimeList, yearOfSelect, quarterAndDel, data2, flag, unit);
            for (int i = 1; i <= flag; i++) {
                time.add("第" + i + "季度");
            }
        } else {
            title = "年度新增与流失客户数量统计";
            xname = "年度";
            //年份和当年新增会员数量
            HashMap<Integer, Integer> yearAndAdd = new HashMap<>();
            //年份和当年注销会员数量
            HashMap<Integer, Integer> yearAndDel = new HashMap<>();
            for (LocalDateTime localDateTime : createTimeList) {
                if (localDateTime.getYear() >= beginYear
                        && localDateTime.getYear() <= endYear) {
                    int yearValue = localDateTime.getYear();
                    yearAndAdd.put(yearValue, yearAndAdd.getOrDefault(yearValue, 0) + 1);
                }
            }
            for (LocalDateTime localDateTime : delTimeList) {
                if (localDateTime.getYear() >= beginYear
                        && localDateTime.getYear() <= endYear) {
                    int yearValue = localDateTime.getYear();
                    yearAndDel.put(yearValue, yearAndDel.getOrDefault(yearValue, 0) + 1);
                }
            }
            if (createTimeList.isEmpty() && delTimeList.isEmpty()) {
                return null;
            }
            for (int i = beginYear; i <= endYear; i++) {
                time.add(i + "年");
                Integer a = yearAndAdd.getOrDefault(i, 0);
                Integer b = yearAndDel.getOrDefault(i, 0);
                data.add(a);
                data2.add(b);
            }
        }
        CardCostVo cardCostVo = new CardCostVo();
        cardCostVo.setTitle(title)
                .setXname(xname)
                .setData(data)
                .setData2(data2)
                .setTime(time);
        return cardCostVo;
    }

    private void totalcount(List<LocalDateTime> timeList, Integer yearOfSelect, HashMap<Integer, Integer> map, List<Integer> data, int flag, Integer unit) {
        for (LocalDateTime localDateTime : timeList) {
            if (localDateTime.getYear() == yearOfSelect) {
                int monthValue = localDateTime.getMonthValue();
                if (unit == 2) {
                    monthValue = (monthValue - 1) / 3 + 1;
                }
                map.put(monthValue, map.getOrDefault(monthValue, 0) + 1);
            }
        }
        for (int i = 1; i <= flag; i++) {
            Integer a = map.getOrDefault(i, 0);
            data.add(a);
        }
    }

    private void timeAndDataCount(Integer yearOfSelect, HashMap<Integer, Integer> timeAndCount, List<String> time, List<Integer> data) {
        if (yearOfSelect == LocalDate.now().getYear()) {
            for (int i = 1; i <= LocalDate.now().getMonthValue(); i++) {
                Integer monthCount = timeAndCount.getOrDefault(i, 0);
                time.add(i + "月");
                data.add(monthCount);
            }
        } else {
            for (int i = 1; i <= 12; i++) {
                Integer monthCount = timeAndCount.getOrDefault(i, 0);
                time.add(i + "月");
                data.add(monthCount);
            }
        }
    }

    private void quarterAndDataCount(Integer yearOfSelect, HashMap<Integer, Integer> quarterAndCount, List<String> time, List<Integer> data) {
        if (yearOfSelect == LocalDate.now().getYear()) {
            for (int i = 1; i <= (LocalDate.now().getMonthValue() - 1) / 3 + 1; i++) {
                Integer monthCost = quarterAndCount.getOrDefault(i, 0);
                time.add("第" + i + "季度");
                data.add(monthCost);
            }
        } else {
            for (int i = 1; i <= 4; i++) {
                Integer monthCost = quarterAndCount.getOrDefault(i, 0);
                time.add("第" + i + "季度");
                data.add(monthCost);
            }
        }
    }
}
