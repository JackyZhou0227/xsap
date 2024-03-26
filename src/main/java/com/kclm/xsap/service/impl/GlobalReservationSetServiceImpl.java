package com.kclm.xsap.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kclm.xsap.consts.ReservationAction;
import com.kclm.xsap.mapper.GlobalReservationSetMapper;
import com.kclm.xsap.mapper.ScheduleRecordMapper;
import com.kclm.xsap.model.entity.GlobalReservationSetEntity;
import com.kclm.xsap.service.GlobalReservationSetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
public class GlobalReservationSetServiceImpl extends ServiceImpl<GlobalReservationSetMapper, GlobalReservationSetEntity>
        implements GlobalReservationSetService {

    @Resource
    ScheduleRecordMapper scheduleRecordMapper;

    @Override
    public boolean isNotInPrescriptiveTime(Long id, ReservationAction action) {

        GlobalReservationSetEntity globalSet = this.getById(1);

        if (globalSet == null) {
            throw new RuntimeException("没有全局设置");
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime classStartTime = null;

        switch (action) {
            case MAKE_RESERVATION:
                classStartTime = scheduleRecordMapper.getStartTimeByScheduleId(id);
                log.info("添加预约，课程开始时间：" + classStartTime);
                break;
            case CANCEL_RESERVATION:
                classStartTime = scheduleRecordMapper.getStartTimeByReserveId(id);
                log.info("取消预约，课程开始时间：" + classStartTime);
                break;
        }


        if (now.isAfter(classStartTime)) {
            log.info("课程已开始，无法预约或取消");
            return true;
        }

        switch (action) {
            // 预约时间判断
            case MAKE_RESERVATION:
                log.info("预约时间判断");

                switch (globalSet.getAppointmentStartMode()) {
                    case 1: // 不限制提前预约天数
                        switch (globalSet.getAppointmentDeadlineMode()) {
                            case 1: // 不限制预约截止时间
                                log.info("1，1");
                                return false;
                            case 2: // 限制为上课前xx小时
                                log.info("1，2");
                                long hoursUntilClass = ChronoUnit.HOURS.between(now, classStartTime);
                                return hoursUntilClass < globalSet.getEndHour();
                            case 3: // 限制为上课前xx天xx:xx（时间点）
                                log.info("1，3");
                                long daysUntilClass = ChronoUnit.DAYS.between(now, classStartTime);
                                if (daysUntilClass <= globalSet.getEndDay()) {
                                    LocalDateTime deadline = classStartTime.minusDays(globalSet.getEndDay())
                                            .withHour(globalSet.getEndTime().getHour())
                                            .withMinute(globalSet.getEndTime().getMinute());
                                    return now.isAfter(deadline);
                                }
                                return true;
                            default:
                                return true;
                        }
                    case 2: // 限制提前预约天数

                        long daysUntilClass = ChronoUnit.DAYS.between(now, classStartTime);

                        if (daysUntilClass >= globalSet.getStartDay()) {
                            return true;
                        }

                        switch (globalSet.getAppointmentDeadlineMode()) {
                            case 1: // 不限制预约截止时间
                                log.info("2，1");
                                return false;
                            case 2: // 限制为上课前xx小时
                                log.info("2，2");
                                long hoursUntilClass = ChronoUnit.HOURS.between(now, classStartTime);
                                return hoursUntilClass < globalSet.getEndHour();
                            case 3: // 限制为上课前xx天xx:xx（时间点）
                                log.info("2，3");
                                LocalDateTime deadline = classStartTime.minusDays(globalSet.getEndDay())
                                        .withHour(globalSet.getEndTime().getHour())
                                        .withMinute(globalSet.getEndTime().getMinute());
                                return now.isAfter(deadline);
                            default:
                                return true;
                        }
                    default:
                        return true;
                }

            case CANCEL_RESERVATION:
                log.info("取消预约时间判断");
                // 取消预约时间判断
                switch (globalSet.getAppointmentCancelMode()) {
                    case 1:
                        log.info("1");
                        return false;
                    case 2:
                        log.info("2");
                        long hoursUntilClass = ChronoUnit.HOURS.between(now, classStartTime);
                        return hoursUntilClass < globalSet.getCancelHour();
                    case 3:
                        log.info("3");
                        long daysUntilClass = ChronoUnit.DAYS.between(now, classStartTime);
                        if (daysUntilClass <= globalSet.getCancelDay()) {
                            // 创建一个临时的 LocalDateTime 对象表示课程当天的取消截止时刻
                            LocalDateTime cancelDeadlineBase = classStartTime.toLocalDate().atTime(globalSet.getCancelTime());

                            // 将取消截止时刻转换为距离课程开始前的具体时间
                            LocalDateTime cancelDeadline = cancelDeadlineBase.minusDays(globalSet.getCancelDay());

                            return now.isAfter(cancelDeadline);
                        }
                        return true;
                    default:
                        return true;
                }

            default:
                return true;
        }
    }
}
