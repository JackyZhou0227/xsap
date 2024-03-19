package com.kclm.xsap.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kclm.xsap.model.entity.ReservationRecordEntity;
import com.kclm.xsap.model.vo.ReserveInfoVo;
import com.kclm.xsap.model.vo.ScheduleDetailReservedVo;

import java.util.List;

public interface ReservationRecordService extends IService<ReservationRecordEntity> {
//    boolean remove(QueryWrapper<ReservationRecordEntity> scheduleId);
    List<ScheduleDetailReservedVo> getScheduleDetailReservedVoByScheduleId(Long scheduleId);

    boolean updateStatusById(Long id);

    List<ReserveInfoVo> getReserveInfoVoListByMemberId(Long memberId);
}
