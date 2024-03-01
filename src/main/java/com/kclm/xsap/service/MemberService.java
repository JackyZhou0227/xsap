package com.kclm.xsap.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kclm.xsap.entity.MemberEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public interface MemberService {
    Arrays list(QueryWrapper<MemberEntity> in);
}
