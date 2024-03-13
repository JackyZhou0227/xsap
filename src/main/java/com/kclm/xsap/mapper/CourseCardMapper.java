package com.kclm.xsap.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kclm.xsap.model.entity.CourseCardEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseCardMapper extends BaseMapper<CourseCardEntity> {
    int insertCourseAndCards(Long courseId, List<Long> cardIdList);
}
