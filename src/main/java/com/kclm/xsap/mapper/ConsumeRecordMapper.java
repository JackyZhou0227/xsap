package com.kclm.xsap.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kclm.xsap.model.entity.ConsumeRecordEntity;
import com.kclm.xsap.model.vo.ConsumeInfoVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ConsumeRecordMapper extends BaseMapper<ConsumeRecordEntity> {
    List<ConsumeInfoVo> getConsumeInfoByMemberId(Long memberId);
}
