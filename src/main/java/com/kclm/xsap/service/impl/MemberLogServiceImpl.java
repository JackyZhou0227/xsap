package com.kclm.xsap.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kclm.xsap.mapper.*;
import com.kclm.xsap.model.entity.ClassRecordEntity;
import com.kclm.xsap.model.entity.ConsumeRecordEntity;
import com.kclm.xsap.model.entity.MemberLogEntity;
import com.kclm.xsap.model.entity.RechargeRecordEntity;
import com.kclm.xsap.model.vo.ConsumeEnsureVo;
import com.kclm.xsap.model.vo.ConsumeOptVo;
import com.kclm.xsap.model.vo.OperateRecordVo;
import com.kclm.xsap.model.vo.RechargeOptVo;
import com.kclm.xsap.service.ConsumeRecordService;
import com.kclm.xsap.service.MemberLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MemberLogServiceImpl extends ServiceImpl<MemberLogMapper, MemberLogEntity> implements MemberLogService {

    @Resource
    private MemberBindRecordMapper memberBindRecordMapper;


    @Resource
    private ConsumeRecordService consumeRecordService;

    @Resource
    private ConsumeRecordMapper consumeRecordMapper;

    @Resource
    private ClassRecordMapper classRecordMapper;

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private RechargeRecordMapper rechargeRecordMapper;

    @Override
    public List<OperateRecordVo> getOperateRecordVoByMemberIdAndCardId(Long memberId, Long cardId) {
        return baseMapper.getOperateRecordVoByMemberIdAndCardId(memberId, cardId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean rechargeOpt(RechargeOptVo rechargeOptVo) {


        MemberLogEntity memberLogEntity = new MemberLogEntity(rechargeOptVo);
        memberLogEntity.setLastModifyTime(LocalDateTime.now());

        ConsumeRecordEntity consumeRecordEntity = new ConsumeRecordEntity(rechargeOptVo);
        consumeRecordEntity.setLastModifyTime(LocalDateTime.now());

        RechargeRecordEntity rechargeRecordEntity = new RechargeRecordEntity(rechargeOptVo);
        rechargeRecordEntity.setLastModifyTime(LocalDateTime.now());
        try {
            boolean result1 = baseMapper.insert(memberLogEntity) > 0;
            boolean result2 = memberBindRecordMapper.addCountAndDay(rechargeOptVo.getMemberBindId(), rechargeOptVo.getAddCount(), rechargeOptVo.getAddDay());
            boolean result3 = consumeRecordMapper.insert(consumeRecordEntity) > 0;
            boolean result4 = rechargeRecordMapper.insert(rechargeRecordEntity) > 0;
            return result1 && result2 && result3 && result4;

        } catch (Exception e) {
            log.error("充值操作失败");
            throw new RuntimeException("充值操作失败", e);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean consumeOpt(ConsumeOptVo consumeOptVo) {
        MemberLogEntity memberLogEntity = new MemberLogEntity(consumeOptVo);
        memberLogEntity.setLastModifyTime(LocalDateTime.now());

        ConsumeRecordEntity consumeRecordEntity = new ConsumeRecordEntity(consumeOptVo);
        consumeRecordEntity.setLastModifyTime(LocalDateTime.now());

        try {

            boolean result1 = baseMapper.insert(memberLogEntity) > 0;
            boolean result2 = memberBindRecordMapper.deductCount(consumeOptVo.getCardBindId(), consumeOptVo.getCardCountChange());
            boolean result3 = consumeRecordMapper.insert(consumeRecordEntity) > 0;
            return result1 && result2 && result3;

        } catch (Exception e) {
            log.error("会员卡扣费操作失败");
            throw new RuntimeException("会员卡扣费操作失败", e);
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean consumeEnsureAll(Long scheduleId, String operator) {

        Integer cardCountChange = courseMapper.getCountByScheduleId(scheduleId);
        try {
            boolean result1 = classRecordMapper.consumeEnsureAll(scheduleId);

            List<ClassRecordEntity> classRecordEntities = classRecordMapper.getClassRecordEntityListByScheduleId(scheduleId);

            List<MemberLogEntity> memberLogEntities = new ArrayList<>();
            List<ConsumeRecordEntity> consumeRecordEntities = new ArrayList<>();

            for (ClassRecordEntity classRecordEntity : classRecordEntities) {

                MemberLogEntity memberLogEntity = new MemberLogEntity(classRecordEntity);
                memberLogEntity.setOperator(operator);
                memberLogEntity.setCardCountChange(cardCountChange);
                memberLogEntity.setLastModifyTime(LocalDateTime.now());
                memberLogEntities.add(memberLogEntity);

                ConsumeRecordEntity consumeRecordEntity = new ConsumeRecordEntity(classRecordEntity);
                consumeRecordEntity.setOperator(operator);
                consumeRecordEntity.setCardCountChange(cardCountChange);
                consumeRecordEntity.setScheduleId(scheduleId);
                consumeRecordEntity.setLogId(memberLogEntity.getId());
                consumeRecordEntity.setLastModifyTime(LocalDateTime.now());
                consumeRecordEntities.add(consumeRecordEntity);

                memberBindRecordMapper.deductCount(classRecordEntity.getBindCardId(), cardCountChange);
            }

            boolean result2 = saveBatch(memberLogEntities);

            boolean result3 = consumeRecordService.saveBatch(consumeRecordEntities);

            return result1 && result2 && result3;

        } catch (Exception e) {
            throw new RuntimeException("一键扣费失败", e);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean consumeEnsure(ConsumeEnsureVo consumeEnsureVo) {
        ClassRecordEntity classRecordEntity = classRecordMapper.selectById(consumeEnsureVo.getClassId());

        if (classRecordEntity != null) {
            try {
                MemberLogEntity memberLogEntity = new MemberLogEntity(classRecordEntity);
                memberLogEntity.setOperator(consumeEnsureVo.getOperator());
                memberLogEntity.setCardCountChange(consumeEnsureVo.getCardCountChange());
                memberLogEntity.setInvolveMoney(consumeEnsureVo.getAmountOfConsumption());
                memberLogEntity.setLastModifyTime(LocalDateTime.now());

                boolean result1 = baseMapper.insert(memberLogEntity) > 0;

                ConsumeRecordEntity consumeRecordEntity = new ConsumeRecordEntity(classRecordEntity);
                consumeRecordEntity.setOperator(consumeEnsureVo.getOperator());
                consumeRecordEntity.setCardCountChange(consumeEnsureVo.getCardCountChange());
                consumeRecordEntity.setMoneyCost(consumeEnsureVo.getAmountOfConsumption());
                consumeRecordEntity.setScheduleId(classRecordEntity.getScheduleId());
                consumeRecordEntity.setLogId(memberLogEntity.getId());
                consumeRecordEntity.setLastModifyTime(LocalDateTime.now());

                boolean result2 = consumeRecordService.save(consumeRecordEntity);

                boolean result3 = memberBindRecordMapper.deductCount(classRecordEntity.getBindCardId(), consumeEnsureVo.getCardCountChange());

                classRecordEntity.setCheckStatus(1);
                boolean result4 = classRecordMapper.updateById(classRecordEntity) > 0;

                return result1 && result2 && result3 && result4;
            } catch (Exception e) {
                throw new RuntimeException("单独扣费失败", e);
            }
        } else {
            return false;
        }

    }
}
