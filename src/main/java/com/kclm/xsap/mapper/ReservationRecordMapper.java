package com.kclm.xsap.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kclm.xsap.model.entity.ReservationRecordEntity;
import com.kclm.xsap.model.vo.ReserveInfoVo;
import com.kclm.xsap.model.vo.ScheduleDetailReservedVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReservationRecordMapper extends BaseMapper<ReservationRecordEntity> {
    List<ScheduleDetailReservedVo> getAllScheduleDetailReservedVoByScheduleId(Long scheduleId);

    List<ReserveInfoVo> getReserveInfoVoListByMemberId(Long memberId);

    ReservationRecordEntity getReservationRecordEntityByMemberScheduleCardId(Long memberId, Long scheduleId, Long cardId);

    List<ReservationRecordEntity> getReservationByTeacherId(Long teacherId);

    List<ReservationRecordEntity> getOverdueReservationByScheduleId(Long scheduleId);

    Long getReserveId(Long memberId, Long scheduleId);
}
