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
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(rollbackFor = Exception.class)
    public boolean saveCourseDTO(CourseDTO courseDTO) {
        CourseEntity courseEntity = new CourseEntity(courseDTO);
        courseEntity.setCreateTime(LocalDateTime.now());
        courseEntity.setLastModifyTime(LocalDateTime.now());
        //Mybatis-plus会自动赋值version
        List<Long> cardIdList = courseDTO.getCardListStr();
        Long courseId = courseEntity.getId();
        log.info("courseId:{}", courseId);
        try {
            courseMapper.insert(courseEntity);
            return courseCardMapper.insertCourseAndCards(courseEntity.getId(), cardIdList) > 0;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("save course failure", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateCourseDTO(CourseDTO courseDTO) {
        log.info("courseDTO的id为"+courseDTO.getId());
        CourseEntity courseEntity = new CourseEntity(courseDTO);
        courseEntity.setId(courseDTO.getId());
        log.info("courseEntity的id为"+courseEntity.getId());

        courseEntity.setLastModifyTime(LocalDateTime.now());
        List<Long> cardIdList = courseDTO.getCardListStr();

        try {
            courseMapper.updateById(courseEntity);
            courseCardMapper.deleteAllCardsByCourseId(courseEntity.getId());
            return courseCardMapper.insertCourseAndCards(courseEntity.getId(), cardIdList) > 0;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("update course failure", e);
        }
    }

    @Override
    public List<Long> getAllCourseIds() {
        return courseMapper.getAllCourseIds();
    }
}
