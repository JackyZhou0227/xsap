package com.kclm.xsap.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kclm.xsap.entity.ScheduleRecordEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ScheduleRecordService {
    List<ScheduleRecordEntity> list(QueryWrapper<ScheduleRecordEntity> teacherId);

    boolean remove(QueryWrapper<ScheduleRecordEntity> teacherId);

    boolean removeByIds(List<Long> scheduleIds);
}
