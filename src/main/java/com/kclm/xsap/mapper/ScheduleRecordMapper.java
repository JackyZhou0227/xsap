package com.kclm.xsap.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kclm.xsap.model.entity.ScheduleRecordEntity;
import com.kclm.xsap.model.vo.CourseScheduleVo;
import com.kclm.xsap.model.vo.ScheduleDetailsVo;
import com.kclm.xsap.model.vo.ScheduleForConsumeSearchVo;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ScheduleRecordMapper extends BaseMapper<ScheduleRecordEntity> {
    List<CourseScheduleVo> listAllCourseScheduleVo();

    List<ScheduleRecordEntity> getScheduleRecordByDate(LocalDate date);

    ScheduleDetailsVo getScheduleDetailsVoById(Long id);

    List<ScheduleForConsumeSearchVo> getScheduleForConsumeSearchVoByKeyword(String keyword);

    LocalDateTime getStartTimeByScheduleId(Long scheduleId);

    LocalDateTime getStartTimeByReserveId(Long reserveId);

    boolean updateOrderNums(Long scheduleId, Integer orderNums);

    List<ScheduleRecordEntity> getScheduleRecordByTeacherIdAndDate(Long teacherId, LocalDate startDate);
}
