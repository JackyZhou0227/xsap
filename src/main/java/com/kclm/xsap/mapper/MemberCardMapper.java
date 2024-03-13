package com.kclm.xsap.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kclm.xsap.model.entity.MemberCardEntity;
import com.kclm.xsap.model.vo.CardInfoVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberCardMapper extends BaseMapper<MemberCardEntity> {

    List<MemberCardEntity> getCardsByMemberId(Long memberId);

    List<CardInfoVo> getCardsInfoByMemberId(Long memberId);

    List<Long> getBindCardIdsByCourseId(Long courseId);
}
