package com.kclm.xsap.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kclm.xsap.model.entity.MemberBindRecordEntity;
import com.kclm.xsap.model.vo.CardTipVo;

public interface MemberBindRecordService extends IService<MemberBindRecordEntity> {
    boolean updateBindStatus(Long memberId, Long bindId, Integer status,String operator);

    CardTipVo getCardTip(Long bindId, Long scheduleId);

    boolean isAllowToDelete(Long cardId);

    boolean doBind(MemberBindRecordEntity memberBindRecordEntity);
}
