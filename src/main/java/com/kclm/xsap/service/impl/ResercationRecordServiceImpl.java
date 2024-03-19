package com.kclm.xsap.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kclm.xsap.mapper.ReservationRecordMapper;
import com.kclm.xsap.model.entity.ReservationRecordEntity;
import com.kclm.xsap.model.vo.ReserveInfoVo;
import com.kclm.xsap.model.vo.ScheduleDetailReservedVo;
import com.kclm.xsap.service.ReservationRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ResercationRecordServiceImpl extends ServiceImpl<ReservationRecordMapper, ReservationRecordEntity> implements ReservationRecordService {

    @Resource
    ReservationRecordMapper reservationRecordMapper;

    @Override
    public List<ScheduleDetailReservedVo> getScheduleDetailReservedVoByScheduleId(Long scheduleId) {
        return reservationRecordMapper.getAllScheduleDetailReservedVoByScheduleId(scheduleId);
    }

    @Override
    public boolean updateStatusById(Long id) {
        ReservationRecordEntity reservationRecordEntity = this.getById(id);

        if (reservationRecordEntity.getStatus() == 0) {
            reservationRecordEntity.setStatus(1);
            reservationRecordEntity.setLastModifyTime(LocalDateTime.now());
            reservationRecordEntity.setReserveNums(reservationRecordEntity.getReserveNums() + 1);
            return updateById(reservationRecordEntity);
        } else {
            return false;
        }
    }

    @Override
    public List<ReserveInfoVo> getReserveInfoVoListByMemberId(Long memberId) {
        return reservationRecordMapper.getReserveInfoVoListByMemberId(memberId);
    }
}
