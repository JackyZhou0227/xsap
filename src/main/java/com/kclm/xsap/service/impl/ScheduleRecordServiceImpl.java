package com.kclm.xsap.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kclm.xsap.mapper.ScheduleRecordMapper;
import com.kclm.xsap.model.entity.ScheduleRecordEntity;
import com.kclm.xsap.model.vo.CourseScheduleVo;
import com.kclm.xsap.model.vo.ScheduleDetailsVo;
import com.kclm.xsap.service.ScheduleRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
public class ScheduleRecordServiceImpl extends ServiceImpl<ScheduleRecordMapper, ScheduleRecordEntity> implements ScheduleRecordService {

    private final static Logger log = LoggerFactory.getLogger(ScheduleRecordServiceImpl.class);
    @Resource
    private ScheduleRecordMapper scheduleRecordMapper;


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
        if (sourceScheduleRecords== null || sourceScheduleRecords.isEmpty()){
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
            throw new RuntimeException("复制课程失败",e);
        }
    }

    @Override
    public ScheduleDetailsVo getScheduleDetailsVoById(Long id) {
        return (ScheduleDetailsVo) scheduleRecordMapper.getScheduleDetailsVoById(id);
    }


}