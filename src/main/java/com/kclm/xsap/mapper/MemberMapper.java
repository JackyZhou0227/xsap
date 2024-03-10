package com.kclm.xsap.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kclm.xsap.model.entity.MemberEntity;
import com.kclm.xsap.model.vo.MemberVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper extends BaseMapper<MemberEntity> {
    List<MemberVo> listAllMemberVo();

    List<MemberEntity> searchMembersByNameOrPhone(String keyword);
}
