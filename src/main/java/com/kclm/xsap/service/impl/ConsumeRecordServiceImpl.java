package com.kclm.xsap.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kclm.xsap.mapper.ConsumeRecordMapper;
import com.kclm.xsap.model.entity.ConsumeRecordEntity;
import com.kclm.xsap.model.vo.ConsumeInfoVo;
import com.kclm.xsap.service.ConsumeRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ConsumeRecordServiceImpl extends ServiceImpl<ConsumeRecordMapper, ConsumeRecordEntity> implements ConsumeRecordService {

    @Resource
    private ConsumeRecordMapper consumeRecordMapper;

    @Override
    public List<ConsumeInfoVo> getConsumeInfoByMemberId(Long memberId) {
        return consumeRecordMapper.getConsumeInfoByMemberId(memberId);
    }
}
