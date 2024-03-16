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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean copySchedule(String sourceDateStr, String targetDateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate sourceDate = LocalDate.parse(sourceDateStr, formatter);
        LocalDate targetDate = LocalDate.parse(targetDateStr, formatter);
        List<ScheduleRecordEntity> sourceScheduleRecords = scheduleRecordMapper.getScheduleRecordByDate(sourceDate);
        if (sourceScheduleRecords== null || sourceScheduleRecords.isEmpty()){
            log.info("源日期{}没有课程", sourceDateStr);
            return false;
        }
        List<ScheduleRecordEntity> targetScheduleRecords = new ArrayList<>();

        for (ScheduleRecordEntity sourceScheduleRecord : sourceScheduleRecords) {
            sourceScheduleRecord.setStartDate(targetDate);
            sourceScheduleRecord.setCreateTime(LocalDateTime.now());
            sourceScheduleRecord.setLastModifyTime(LocalDateTime.now());
            targetScheduleRecords.add(sourceScheduleRecord);
        }

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