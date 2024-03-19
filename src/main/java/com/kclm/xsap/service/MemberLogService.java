package com.kclm.xsap.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kclm.xsap.model.entity.MemberLogEntity;
import com.kclm.xsap.model.vo.OperateRecordVo;

import java.util.List;

public interface MemberLogService extends IService<MemberLogEntity> {
    List<OperateRecordVo> getOperateRecordVoByMemberIdAndCardId(Long memberId, Long cardId);
}
