package com.kclm.xsap.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kclm.xsap.model.entity.MemberEntity;
import com.kclm.xsap.model.entity.ReservationRecordEntity;
import com.kclm.xsap.model.vo.MemberCardStatisticsVo;
import com.kclm.xsap.model.vo.MemberVo;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface MemberMapper extends BaseMapper<MemberEntity> {
    List<MemberVo> listAllMemberVo();

    List<MemberEntity> searchMembersByNameOrPhone(String keyword);

    List<ReservationRecordEntity> getReservationByMemberId(Long memberId);

    List<LocalDateTime> delTimeList();

    List<MemberCardStatisticsVo> getMemberCardStatistics();
}
