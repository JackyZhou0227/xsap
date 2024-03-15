package com.kclm.xsap.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kclm.xsap.model.entity.CourseCardEntity;

import java.util.List;

public interface CourseCardService extends IService<CourseCardEntity> {
    List<Long> selectCardIdsByCourseId(Long courseId);

    List<Long> selectCourseIdsByCardId(Long cardId);
}
