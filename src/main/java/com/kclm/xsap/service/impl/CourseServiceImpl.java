package com.kclm.xsap.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kclm.xsap.mapper.CourseCardMapper;
import com.kclm.xsap.mapper.CourseMapper;
import com.kclm.xsap.model.dto.CourseDTO;
import com.kclm.xsap.model.entity.CourseEntity;
import com.kclm.xsap.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, CourseEntity> implements CourseService {

    private final static Logger log = LoggerFactory.getLogger(CourseServiceImpl.class);

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private CourseCardMapper courseCardMapper;

    @Override
    public boolean isCourseNameExist(String name) {
        return baseMapper.selectCount(new QueryWrapper<CourseEntity>().eq("name", name)) > 0;
    }

    @Override
    public boolean saveCourseDTO(CourseDTO courseDTO) {
        CourseEntity courseEntity = new CourseEntity(courseDTO);
        courseEntity.setCreateTime(LocalDateTime.now());
        courseEntity.setLastModifyTime(LocalDateTime.now());
//        courseEntity.setVersion(1);
        List<Long> cardIdList = courseDTO.getCardListStr();
        courseMapper.insert(courseEntity);
        Long courseId = courseEntity.getId();
        log.info("courseId:{}", courseId);
        return courseCardMapper.insertCourseAndCards(courseId, cardIdList) > 0;
    }
}
