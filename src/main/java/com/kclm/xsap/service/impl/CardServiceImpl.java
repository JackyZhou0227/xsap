package com.kclm.xsap.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kclm.xsap.model.entity.MemberCardEntity;
import com.kclm.xsap.mapper.MemberCardMapper;
import com.kclm.xsap.service.CardService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CardServiceImpl extends ServiceImpl<MemberCardMapper, MemberCardEntity> implements CardService {
    @Resource
    private MemberCardMapper memberCardMapper;


}
