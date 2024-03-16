package com.kclm.xsap.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kclm.xsap.model.entity.MemberBindRecordEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberBindRecordMapper extends BaseMapper<MemberBindRecordEntity> {
    List<Long> getBindCardIdsByMemberId(Long memberId);
}
