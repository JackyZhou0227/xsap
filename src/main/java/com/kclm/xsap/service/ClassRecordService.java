package com.kclm.xsap.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kclm.xsap.model.entity.ClassRecordEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;

public interface ClassRecordService {
    Arrays list(QueryWrapper<ClassRecordEntity> eq);
}
