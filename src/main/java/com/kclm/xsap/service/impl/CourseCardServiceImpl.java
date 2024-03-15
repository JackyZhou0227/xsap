package com.kclm.xsap.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kclm.xsap.mapper.CourseCardMapper;
import com.kclm.xsap.model.entity.CourseCardEntity;
import com.kclm.xsap.service.CourseCardService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CourseCardServiceImpl extends ServiceImpl<CourseCardMapper, CourseCardEntity> implements CourseCardService {

    @Resource
    private CourseCardMapper courseCardMapper;

    @Override
    public List<Long> selectCardIdsByCourseId(Long courseId) {
        return courseCardMapper.selectCardIdsByCourseId(courseId);
    }

    @Override
    public List<Long> selectCourseIdsByCardId(Long cardId) {
        return courseCardMapper.selectCourseIdsByCardId(cardId);
    }
}
