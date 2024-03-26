package com.kclm.xsap.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kclm.xsap.mapper.ClassRecordMapper;
import com.kclm.xsap.model.entity.ClassRecordEntity;
import com.kclm.xsap.model.vo.ClassInfoVo;
import com.kclm.xsap.model.vo.ClassRecordVo;
import com.kclm.xsap.model.vo.TeacherClassRecordVo;
import com.kclm.xsap.service.ClassRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ClassRecordServiceImpl extends ServiceImpl<ClassRecordMapper, ClassRecordEntity> implements ClassRecordService {
    @Resource
    private ClassRecordMapper classRecordMapper;


//    @Override
//    public Arrays list(QueryWrapper<ClassRecordEntity> eq) {
//        return null;
//    }

    @Override
    public List<ClassInfoVo> getClassInfoVoListByMemberId(Long memberId) {
        return baseMapper.getClassInfoVoListByMemberId(memberId);
    }

    @Override
    public List<ClassRecordVo> getClassRecordVoListByScheduleId(Long scheduleId) {
        return baseMapper.getClassRecordVoListByScheduleId(scheduleId);
    }

    @Override
    public List<TeacherClassRecordVo> getTeacherClassRecordVoListByTeacherId(Long teacherId) {
        return baseMapper.getTeacherClassRecordVoListByTeacherId(teacherId);
    }

    @Override
    public Integer getAmountsPayableByClassId(Long classId) {
        return baseMapper.getAmountsPayableByClassId(classId);
    }
}
