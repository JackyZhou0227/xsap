package com.kclm.xsap.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kclm.xsap.mapper.MemberLogMapper;
import com.kclm.xsap.model.entity.MemberLogEntity;
import com.kclm.xsap.model.vo.OperateRecordVo;
import com.kclm.xsap.service.MemberLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MemberLogServiceImpl extends ServiceImpl<MemberLogMapper, MemberLogEntity> implements MemberLogService{


    @Override
    public List<OperateRecordVo> getOperateRecordVoByMemberIdAndCardId(Long memberId, Long cardId) {
        return baseMapper.getOperateRecordVoByMemberIdAndCardId(memberId, cardId);
    }
}
