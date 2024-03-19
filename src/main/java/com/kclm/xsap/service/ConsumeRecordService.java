package com.kclm.xsap.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kclm.xsap.model.entity.ConsumeRecordEntity;
import com.kclm.xsap.model.vo.ConsumeInfoVo;

import java.util.List;

public interface ConsumeRecordService extends IService<ConsumeRecordEntity> {
    List<ConsumeInfoVo> getConsumeInfoByMemberId(Long memberId);
}
