package com.kclm.xsap.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kclm.xsap.model.entity.ClassRecordEntity;
import com.kclm.xsap.model.vo.ClassInfoVo;
import com.kclm.xsap.model.vo.ClassRecordVo;
import com.kclm.xsap.model.vo.TeacherClassRecordVo;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

public interface ClassRecordService extends IService<ClassRecordEntity> {
//    Arrays list(QueryWrapper<ClassRecordEntity> eq);
    List<ClassInfoVo> getClassInfoVoListByMemberId(Long memberId);

    List<ClassRecordVo> getClassRecordVoListByScheduleId(Long scheduleId);

    List<TeacherClassRecordVo> getTeacherClassRecordVoListByTeacherId(Long teacherId);

    Integer getAmountsPayableByClassId(Long classId);
}
