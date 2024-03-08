package com.kclm.xsap.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kclm.xsap.mapper.ScheduleRecordMapper;
import com.kclm.xsap.model.entity.ScheduleRecordEntity;
import com.kclm.xsap.service.ScheduleRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class ScheduleRecordServiceImpl extends ServiceImpl<ScheduleRecordMapper, ScheduleRecordEntity> implements ScheduleRecordService {
    @Resource
    private ScheduleRecordMapper scheduleRecordMapper;

}