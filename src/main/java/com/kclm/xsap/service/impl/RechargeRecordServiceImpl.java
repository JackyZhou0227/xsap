package com.kclm.xsap.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kclm.xsap.mapper.RechargeRecordMapper;
import com.kclm.xsap.model.entity.RechargeRecordEntity;
import com.kclm.xsap.service.RechargeRecordService;
import org.springframework.stereotype.Service;

@Service
public class RechargeRecordServiceImpl extends ServiceImpl<RechargeRecordMapper, RechargeRecordEntity> implements RechargeRecordService {
}
