package com.kclm.xsap.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kclm.xsap.model.entity.ScheduleRecordEntity;
import com.kclm.xsap.model.vo.CourseScheduleVo;
import com.kclm.xsap.model.vo.ScheduleDetailsVo;
import com.kclm.xsap.model.vo.ScheduleForConsumeSearchVo;

import java.util.List;

public interface ScheduleRecordService extends IService<ScheduleRecordEntity> {
    List<CourseScheduleVo> listAllCourseScheduleVo();

    boolean copySchedule(String sourceDateStr, String targetDateStr);

    ScheduleDetailsVo getScheduleDetailsVoById(Long id);

    List<ScheduleForConsumeSearchVo> getScheduleForConsumeSearchVoByKeyword(String keyword);

    void initClassRecord(Long scheduleId);

    boolean isTeacherBusy(ScheduleRecordEntity scheduleRecordEntity);
}
