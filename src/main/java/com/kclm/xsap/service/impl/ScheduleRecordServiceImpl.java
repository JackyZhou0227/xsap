package com.kclm.xsap.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kclm.xsap.mapper.ClassRecordMapper;
import com.kclm.xsap.mapper.CourseMapper;
import com.kclm.xsap.mapper.ReservationRecordMapper;
import com.kclm.xsap.mapper.ScheduleRecordMapper;
import com.kclm.xsap.model.entity.ClassRecordEntity;
import com.kclm.xsap.model.entity.CourseEntity;
import com.kclm.xsap.model.entity.ReservationRecordEntity;
import com.kclm.xsap.model.entity.ScheduleRecordEntity;
import com.kclm.xsap.model.vo.CourseScheduleVo;
import com.kclm.xsap.model.vo.ScheduleDetailsVo;
import com.kclm.xsap.model.vo.ScheduleForConsumeSearchVo;
import com.kclm.xsap.service.ClassRecordService;
import com.kclm.xsap.service.ScheduleRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@Slf4j
public class ScheduleRecordServiceImpl extends ServiceImpl<ScheduleRecordMapper, ScheduleRecordEntity> implements ScheduleRecordService {

    @Resource
    private ScheduleRecordMapper scheduleRecordMapper;

    @Resource
    private ClassRecordMapper classRecordMapper;

    @Resource
    private ClassRecordService classRecordService;

    @Resource
    private ReservationRecordMapper reservationRecordMapper;

    @Resource
    private CourseMapper courseMapper;

    @Override
    public List<CourseScheduleVo> listAllCourseScheduleVo() {
        return scheduleRecordMapper.listAllCourseScheduleVo();
    }

    /**
     * 复制指定日期的课程安排到另一个日期。
     *
     * @param sourceDateStr 源日期字符串，格式为"yyyy-MM-dd"
     * @param targetDateStr 目标日期字符串，格式为"yyyy-MM-dd"
     * @return 如果复制成功返回true，如果源日期没有课程安排返回false
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean copySchedule(String sourceDateStr, String targetDateStr) {
        // 定义日期格式并解析输入的字符串为LocalDate类型
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate sourceDate = LocalDate.parse(sourceDateStr, formatter);
        LocalDate targetDate = LocalDate.parse(targetDateStr, formatter);

        // 根据源日期查询课程安排记录
        List<ScheduleRecordEntity> sourceScheduleRecords = scheduleRecordMapper.getScheduleRecordByDate(sourceDate);
        // 检查源日期是否有课程安排，若无则记录信息并返回false
        if (sourceScheduleRecords == null || sourceScheduleRecords.isEmpty()) {
            log.info("源日期{}没有课程", sourceDateStr);
            return false;
        }

        List<ScheduleRecordEntity> targetScheduleRecords = new ArrayList<>();

        // 遍历源日期的课程安排，复制到目标日期并更新创建和修改时间
        for (ScheduleRecordEntity sourceScheduleRecord : sourceScheduleRecords) {
            sourceScheduleRecord.setStartDate(targetDate);
            sourceScheduleRecord.setCreateTime(LocalDateTime.now());
            sourceScheduleRecord.setLastModifyTime(LocalDateTime.now());
            targetScheduleRecords.add(sourceScheduleRecord);
        }

        // 尝试批量保存目标日期的课程安排，如果失败则抛出运行时异常
        try {
            return saveBatch(targetScheduleRecords);
        } catch (Exception e) {
            throw new RuntimeException("复制课程失败", e);
        }
    }

    @Override
    public ScheduleDetailsVo getScheduleDetailsVoById(Long id) {
        return scheduleRecordMapper.getScheduleDetailsVoById(id);
    }

    @Override
    public List<ScheduleForConsumeSearchVo> getScheduleForConsumeSearchVoByKeyword(String keyword) {
        return scheduleRecordMapper.getScheduleForConsumeSearchVoByKeyword(keyword);
    }

    @Override
    public void initClassRecord(Long scheduleId) {
        log.info("初始化上课记录");

        // 获取所有过期的预约记录
        List<ReservationRecordEntity> reservationRecordEntities = reservationRecordMapper.getOverdueReservationByScheduleId(scheduleId);

        // 获取所有已存在的上课记录
        List<ClassRecordEntity> existingClassRecordEntities = classRecordMapper.getClassRecordEntityListByScheduleId(scheduleId);

        // 创建一个HashSet来快速查找已存在的上课记录组合
        Set<String> existingRecordCombos = new HashSet<>();
        for (ClassRecordEntity classRecordEntity : existingClassRecordEntities) {
            existingRecordCombos.add(
                    classRecordEntity.getMemberId() + "_" +
                            classRecordEntity.getScheduleId() + "_" +
                            classRecordEntity.getBindCardId()
            );
        }

        // 初始化新的上课记录列表
        List<ClassRecordEntity> newClassRecordEntities = new ArrayList<>();

        // 遍历预约记录，只处理没有对应上课记录的记录
        for (ReservationRecordEntity reservationRecordEntity : reservationRecordEntities) {
            String recordCombo = reservationRecordEntity.getMemberId() + "_" +
                    reservationRecordEntity.getScheduleId() + "_" +
                    reservationRecordEntity.getCardId();

            // 如果这条预约记录还没有对应的上课记录，则添加到新记录列表
            if (!existingRecordCombos.contains(recordCombo)) {
                ClassRecordEntity classRecordEntity = new ClassRecordEntity(reservationRecordEntity);
                classRecordEntity.setLastModifyTime(LocalDateTime.now());
                newClassRecordEntities.add(classRecordEntity);
            }
        }

        log.info("初始化新的上课记录数量：" + newClassRecordEntities.size());
        classRecordService.saveOrUpdateBatch(newClassRecordEntities);

    }

    @Override
    public boolean isTeacherBusy(ScheduleRecordEntity scheduleRecordEntity) {
        LocalTime startOfClass = scheduleRecordEntity.getClassTime();
        LocalTime endOfClass = startOfClass.plusMinutes(scheduleRecordEntity.getCourseDuration());

        List<ScheduleRecordEntity> teacherScheduleOnSameDay = scheduleRecordMapper.getScheduleRecordByTeacherIdAndDate(scheduleRecordEntity.getTeacherId(), scheduleRecordEntity.getStartDate());
        if (teacherScheduleOnSameDay.isEmpty()){
            log.info("教师没有排课");
            return false;
        }

        // 检查教师在给定时间段内是否已有排课冲突
        for (ScheduleRecordEntity existingSchedule : teacherScheduleOnSameDay) {
            LocalTime existingStart = existingSchedule.getClassTime();
            LocalTime existingEnd = existingStart.plusMinutes(courseMapper.selectById(existingSchedule.getCourseId()).getDuration()).plusMinutes(10);

            // 判断新排课时间和已排课时间是否有重叠
            if ((startOfClass.isBefore(existingEnd) && endOfClass.isAfter(existingStart))
                    || (existingStart.isBefore(endOfClass) && existingEnd.isAfter(startOfClass))) {
                log.info("教师忙碌");
                // 时间段有重叠，则教师忙碌
                return true;
            }
        }
        log.info("教师不忙碌");
        // 若无重叠则教师当前时段可用
        return false;
    }


}