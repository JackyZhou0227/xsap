package com.kclm.xsap.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kclm.xsap.model.entity.ClassRecordEntity;
import com.kclm.xsap.model.vo.ClassInfoVo;
import com.kclm.xsap.model.vo.ClassRecordVo;
import com.kclm.xsap.model.vo.TeacherClassRecordVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClassRecordMapper extends BaseMapper<ClassRecordEntity> {
    List<ClassInfoVo> getClassInfoVoListByMemberId(Long memberId);

    List<ClassRecordVo> getClassRecordVoListByScheduleId(Long scheduleId);

    List<TeacherClassRecordVo> getTeacherClassRecordVoListByTeacherId(Long teacherId);

    List<ClassRecordEntity> getClassRecordEntityListByScheduleId(Long scheduleId);

    boolean consumeEnsureAll(Long scheduleId);

    Integer getAmountsPayableByClassId(Long classId);

    ClassRecordEntity getClassRecordEntityByReserveId(Long reserveId);
}
