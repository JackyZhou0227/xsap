package com.kclm.xsap.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kclm.xsap.model.entity.EmployeeEntity;
import com.kclm.xsap.model.entity.MemberCardEntity;
import com.kclm.xsap.mapper.MemberCardMapper;
import com.kclm.xsap.service.MemberCardService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MemberCardServiceImpl extends ServiceImpl<MemberCardMapper, MemberCardEntity> implements MemberCardService {
    @Resource
    private MemberCardMapper memberCardMapper;


    @Override
    public boolean isCardNameExist(MemberCardEntity memberCardEntity) {
        return baseMapper.selectCount(new QueryWrapper<MemberCardEntity>().eq("name", memberCardEntity.getName())) > 0;
    }
}
