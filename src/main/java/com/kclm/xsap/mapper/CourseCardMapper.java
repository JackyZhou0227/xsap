package com.kclm.xsap.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kclm.xsap.model.entity.CourseCardEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CourseCardMapper extends BaseMapper<CourseCardEntity> {

    int insertCourseAndCards(Long courseId, List<Long> cardIdList);
    int insertCardAndCourses(Long cardId, List<Long> courseIdList);
    List<Long> selectCardIdsByCourseId(Long courseId);

    List<Long> selectCourseIdsByCardId(Long cardId);

    // 删除指定课程的所有绑定卡记录，并返回受影响的行数
    int deleteAllCardsByCourseId(Long courseId);

    int deleteAllCoursesByCardId(Long cardId);


}
