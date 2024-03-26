package com.kclm.xsap.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kclm.xsap.model.entity.CourseEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseMapper extends BaseMapper<CourseEntity> {

    List<Long> getAllCourseIds();

    List<CourseEntity> selectCoursesByKeyword(String keyword);

    Integer getCountByScheduleId(Long scheduleId);
}
