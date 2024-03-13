package com.kclm.xsap.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kclm.xsap.mapper.MemberBindRecordMapper;
import com.kclm.xsap.model.entity.MemberBindRecordEntity;
import com.kclm.xsap.service.MemberBindRecordService;
import org.springframework.stereotype.Service;

@Service
public class MemberBindRecordServiceImpl extends ServiceImpl<MemberBindRecordMapper, MemberBindRecordEntity> implements MemberBindRecordService {
}
