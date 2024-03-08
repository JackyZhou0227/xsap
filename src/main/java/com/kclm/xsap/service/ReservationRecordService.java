package com.kclm.xsap.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kclm.xsap.model.entity.ReservationRecordEntity;
import org.springframework.stereotype.Service;

public interface ReservationRecordService {
    boolean remove(QueryWrapper<ReservationRecordEntity> scheduleId);
}
