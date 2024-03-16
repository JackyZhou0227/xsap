package com.kclm.xsap.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kclm.xsap.mapper.MemberBindRecordMapper;
import com.kclm.xsap.model.entity.MemberBindRecordEntity;
import com.kclm.xsap.service.MemberBindRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MemberBindRecordServiceImpl extends ServiceImpl<MemberBindRecordMapper, MemberBindRecordEntity> implements MemberBindRecordService {

    @Resource
    private MemberBindRecordMapper memberBindRecordMapper;

    @Override
    public boolean updateBindStatus(Long memberId, Long bindId, Integer status) {
        try {
            MemberBindRecordEntity memberBindRecordEntity = getById(bindId);
            memberBindRecordEntity.setActiveStatus(status);
            memberBindRecordEntity.setLastModifyTime(LocalDateTime.now());
            return updateById(memberBindRecordEntity);
        } catch (Exception e) {
            throw new RuntimeException("激活状态修改失败！", e);
        }

    }

}
