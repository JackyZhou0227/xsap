package com.kclm.xsap.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kclm.xsap.mapper.MemberLogMapper;
import com.kclm.xsap.model.entity.MemberLogEntity;
import com.kclm.xsap.service.MemberLogService;
import org.springframework.stereotype.Service;

@Service
public class MemberLogServiceImpl extends ServiceImpl<MemberLogMapper, MemberLogEntity> implements MemberLogService{
}
