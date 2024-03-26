package com.kclm.xsap.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kclm.xsap.mapper.*;
import com.kclm.xsap.model.entity.*;
import com.kclm.xsap.model.vo.ReserveInfoVo;
import com.kclm.xsap.model.vo.ScheduleDetailReservedVo;
import com.kclm.xsap.service.ReservationRecordService;
import com.kclm.xsap.utils.exception.ReservationFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class ResercationRecordServiceImpl extends ServiceImpl<ReservationRecordMapper, ReservationRecordEntity> implements ReservationRecordService {


    @Resource
    private ReservationRecordMapper reservationRecordMapper;

    @Resource
    private ClassRecordMapper classRecordMapper;

    @Resource
    private ScheduleRecordMapper scheduleRecordMapper;

    @Resource
    private MemberMapper memberMapper;

    @Resource
    private MemberBindRecordMapper memberBindRecordMapper;

    @Resource
    private CourseCardMapper courseCardMapper;

    @Resource
    private CourseMapper courseMapper;

    @Override
    public List<ScheduleDetailReservedVo> getScheduleDetailReservedVoByScheduleId(Long scheduleId) {
        return reservationRecordMapper.getAllScheduleDetailReservedVoByScheduleId(scheduleId);
    }

    @Override
    public List<ReserveInfoVo> getReserveInfoVoListByMemberId(Long memberId) {
        return reservationRecordMapper.getReserveInfoVoListByMemberId(memberId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cancelReservation(Long reserveId) {
        ReservationRecordEntity reservationRecordEntity = this.getById(reserveId);

        if (reservationRecordEntity.getStatus() == 0) {
            reservationRecordEntity.setStatus(1);
            reservationRecordEntity.setCancelTimes(reservationRecordEntity.getCancelTimes() + 1);
            reservationRecordEntity.setLastModifyTime(LocalDateTime.now());
            ClassRecordEntity classRecordEntity = classRecordMapper.getClassRecordEntityByReserveId(reservationRecordEntity.getId());
            classRecordEntity.setReserveCheck(1);
            classRecordEntity.setLastModifyTime(LocalDateTime.now());

            try {
                return updateById(reservationRecordEntity) && classRecordMapper.updateById(classRecordEntity) > 0 &&
                        scheduleRecordMapper.updateOrderNums(reservationRecordEntity.getScheduleId(), -1);
            } catch (Exception e) {

                throw new RuntimeException("取消预约出现异常", e);
            }
        } else {
            return false;
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean doReserve(ReservationRecordEntity reservationRecordEntity) {
        ScheduleRecordEntity scheduleRecordEntity = scheduleRecordMapper.selectById(reservationRecordEntity.getScheduleId());
        MemberEntity memberEntity = memberMapper.selectById(reservationRecordEntity.getMemberId());
        int age = LocalDateTime.now().getYear() - memberEntity.getBirthday().getYear();
        if (scheduleRecordEntity.getLimitAge() != 0 && age < scheduleRecordEntity.getLimitAge()) {
            throw new ReservationFailedException("年龄不符合要求!");
        }
        if (!Objects.equals(scheduleRecordEntity.getLimitSex(), "无限制") && !scheduleRecordEntity.getLimitSex().equals(memberEntity.getSex())) {
            throw new ReservationFailedException("性别不符合要求!");
        }
        List<Long> cardIds = courseCardMapper.selectCardIdsByCourseId(scheduleRecordEntity.getCourseId());
        MemberCardEntity memberCardEntity = memberBindRecordMapper.getCardIdByBindId(reservationRecordEntity.getCardId());
        if (!cardIds.contains(memberCardEntity.getId())) {
            throw new ReservationFailedException("该卡无法预约该课程!");
        }
        CourseEntity courseEntity = courseMapper.selectById(scheduleRecordEntity.getCourseId());
        if (scheduleRecordEntity.getOrderNums() >= courseEntity.getContains()) {
            throw new ReservationFailedException("该课程预约人数已满!");
        }

        ReservationRecordEntity oldRecord = baseMapper.getReservationRecordEntityByMemberScheduleCardId(reservationRecordEntity.getMemberId(), reservationRecordEntity.getScheduleId(), reservationRecordEntity.getCardId());

        if (oldRecord == null) {
            reservationRecordEntity.setCardName(memberCardEntity.getName());
            reservationRecordEntity.setCreateTime(LocalDateTime.now());
            reservationRecordEntity.setLastModifyTime(LocalDateTime.now());
            ClassRecordEntity classRecordEntity = new ClassRecordEntity(reservationRecordEntity);
            classRecordEntity.setLastModifyTime(LocalDateTime.now());


            try {
                return save(reservationRecordEntity) && classRecordMapper.insert(classRecordEntity) > 0 &&
                        scheduleRecordMapper.updateOrderNums(reservationRecordEntity.getScheduleId(), 1);
            } catch (Exception e) {
                throw new RuntimeException("预约出现异常", e);
            }
        }
        if (oldRecord.getStatus() == 0) {
            throw new ReservationFailedException("该课程已预约且未取消预约，请勿重复预约!");
        }
        if (oldRecord.getCancelTimes() >= courseEntity.getLimitCounts() && courseEntity.getLimitCounts() != 0) {
            throw new ReservationFailedException("该课程已预约且已取消预约次数达到上限，无法再次预约！");
        }

        oldRecord.setStatus(0);
        oldRecord.setReserveNums(reservationRecordEntity.getReserveNums());
        oldRecord.setNote(reservationRecordEntity.getNote());
        oldRecord.setCardName(memberCardEntity.getName());
        oldRecord.setLastModifyTime(LocalDateTime.now());
        ClassRecordEntity classRecordEntity = classRecordMapper.getClassRecordEntityByReserveId(oldRecord.getId());
        classRecordEntity.setLastModifyTime(LocalDateTime.now());
        classRecordEntity.setReserveCheck(0);


        try {
            return updateById(oldRecord) && classRecordMapper.updateById(classRecordEntity) > 0 &&
                    scheduleRecordMapper.updateOrderNums(oldRecord.getScheduleId(), 1);
        } catch (Exception e) {
            throw new RuntimeException("预约出现异常", e);
        }
    }

    @Override
    public Long getReserveId(Long memberId, Long scheduleId) {
        return reservationRecordMapper.getReserveId(memberId, scheduleId);
    }
}
