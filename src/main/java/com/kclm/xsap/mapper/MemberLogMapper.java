package com.kclm.xsap.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kclm.xsap.model.entity.MemberLogEntity;
import com.kclm.xsap.model.vo.OperateRecordVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberLogMapper extends BaseMapper<MemberLogEntity> {
    List<OperateRecordVo> getOperateRecordVoByMemberIdAndCardId(Long memberId,Long cardId);
}
