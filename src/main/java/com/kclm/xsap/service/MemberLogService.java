package com.kclm.xsap.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kclm.xsap.model.entity.MemberLogEntity;
import com.kclm.xsap.model.vo.ConsumeEnsureVo;
import com.kclm.xsap.model.vo.ConsumeOptVo;
import com.kclm.xsap.model.vo.OperateRecordVo;
import com.kclm.xsap.model.vo.RechargeOptVo;

import java.util.List;

public interface MemberLogService extends IService<MemberLogEntity> {
    List<OperateRecordVo> getOperateRecordVoByMemberIdAndCardId(Long memberId, Long cardId);

    boolean rechargeOpt(RechargeOptVo rechargeOptVo);

    boolean consumeOpt(ConsumeOptVo consumeOptVo);

    boolean consumeEnsureAll(Long scheduleId, String operator);

    boolean consumeEnsure(ConsumeEnsureVo consumeEnsureVo);
}
