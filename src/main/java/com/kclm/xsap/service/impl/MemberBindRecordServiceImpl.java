package com.kclm.xsap.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kclm.xsap.consts.OperateType;
import com.kclm.xsap.mapper.ConsumeRecordMapper;
import com.kclm.xsap.mapper.MemberBindRecordMapper;
import com.kclm.xsap.mapper.MemberLogMapper;
import com.kclm.xsap.model.entity.ConsumeRecordEntity;
import com.kclm.xsap.model.entity.MemberBindRecordEntity;
import com.kclm.xsap.model.entity.MemberLogEntity;
import com.kclm.xsap.model.vo.CardTipVo;
import com.kclm.xsap.service.MemberBindRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Slf4j
@Service
public class MemberBindRecordServiceImpl extends ServiceImpl<MemberBindRecordMapper, MemberBindRecordEntity> implements MemberBindRecordService {

    @Resource
    private MemberBindRecordMapper memberBindRecordMapper;

    @Resource
    private MemberLogMapper memberLogMapper;

    @Resource
    private ConsumeRecordMapper consumeRecordMapper;

    /**
     * 更新成员绑定状态
     *
     * @param memberId 成员ID
     * @param bindId   绑定ID
     * @param status   新的状态值
     * @return 更新成功返回true，失败返回false
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateBindStatus(Long memberId, Long bindId, Integer status, String operator) {
        try {
            // 根据绑定ID获取绑定记录实体
            MemberBindRecordEntity memberBindRecordEntity = getById(bindId);
            // 更新激活状态和最后修改时间
            memberBindRecordEntity.setActiveStatus(status);
            memberBindRecordEntity.setLastModifyTime(LocalDateTime.now());

            MemberLogEntity memberLogEntity = new MemberLogEntity();
            if (status == 1) {
                log.info("该会员卡被激活！");
                memberLogEntity.setType(OperateType.ACTIVATE_MEMBERSHIP_CARD_OPERATION.getMsg());
            } else if (status == 0) {
                log.info("该会员卡被停用！");
                memberLogEntity.setType(OperateType.DEACTIVATE_MEMBERSHIP_CARD_OPERATION.getMsg());
            }
            memberLogEntity.setOperator(operator);
            memberLogEntity.setMemberBindId(bindId);
            memberLogEntity.setCreateTime(LocalDateTime.now());
            memberLogEntity.setCardActiveStatus(status);
            memberLogEntity.setLastModifyTime(LocalDateTime.now());

            // 保存更新后的实体并返回操作结果
            return updateById(memberBindRecordEntity) && memberLogMapper.insert(memberLogEntity) > 0;
        } catch (Exception e) {
            // 捕获异常，抛出运行时异常
            throw new RuntimeException("激活状态修改失败！", e);
        }

    }

    @Override
    public CardTipVo getCardTip(Long bindId, Long scheduleId) {
        return memberBindRecordMapper.getCardTip(bindId, scheduleId);
    }

    @Override
    public boolean isAllowToDelete(Long cardId) {
        return memberBindRecordMapper.getUsableBindRecordByCardId(cardId).isEmpty();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean doBind(MemberBindRecordEntity memberBindRecordEntity) {

        try {
            boolean result1 = save(memberBindRecordEntity);

            MemberLogEntity memberLogEntity = new MemberLogEntity(memberBindRecordEntity);
            memberLogEntity.setLastModifyTime(LocalDateTime.now());
            boolean result2 = memberLogMapper.insert(memberLogEntity) > 0;

            ConsumeRecordEntity consumeRecordEntity = new ConsumeRecordEntity(memberBindRecordEntity);
            consumeRecordEntity.setLogId(memberLogEntity.getId());
            consumeRecordEntity.setLastModifyTime(LocalDateTime.now());
            boolean result3 = consumeRecordMapper.insert(consumeRecordEntity) > 0;

            return result1 && result2 && result3;
        } catch (Exception e) {
            throw new RuntimeException("绑卡失败！", e);
        }
    }

}
