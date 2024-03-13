package com.kclm.xsap.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kclm.xsap.model.entity.MemberCardEntity;
import com.kclm.xsap.model.vo.CardInfoVo;

import java.util.List;

public interface MemberCardService extends IService<MemberCardEntity> {
    boolean isCardNameExist(MemberCardEntity memberCardEntity);

    List<CardInfoVo> getCardsInfoByMemberId(Long memberId);

    List<Long> getBindCardIdsByCourseId(Long courseId);
}