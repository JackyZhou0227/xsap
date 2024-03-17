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

    /**
     * 更新成员绑定状态
     * @param memberId 成员ID
     * @param bindId 绑定ID
     * @param status 新的状态值
     * @return 更新成功返回true，失败返回false
     */
    @Override
    public boolean updateBindStatus(Long memberId, Long bindId, Integer status) {
        try {
            // 根据绑定ID获取绑定记录实体
            MemberBindRecordEntity memberBindRecordEntity = getById(bindId);
            // 更新激活状态和最后修改时间
            memberBindRecordEntity.setActiveStatus(status);
            memberBindRecordEntity.setLastModifyTime(LocalDateTime.now());
            // 保存更新后的实体并返回操作结果
            return updateById(memberBindRecordEntity);
        } catch (Exception e) {
            // 捕获异常，抛出运行时异常
            throw new RuntimeException("激活状态修改失败！", e);
        }

    }

}
