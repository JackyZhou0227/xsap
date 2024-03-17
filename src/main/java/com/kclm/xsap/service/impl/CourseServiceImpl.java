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

    /**
     * 保存课程信息及其关联的卡片列表。
     * @param courseDTO 课程数据传输对象，包含课程的详细信息和关联的卡片ID列表。
     * @return 返回一个布尔值，表示保存是否成功。
     * @throws RuntimeException 如果保存过程中发生异常，则抛出运行时异常。
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveCourseDTO(CourseDTO courseDTO) {
        // 将课程DTO转换为实体对象，并设置创建时间和最后修改时间
        CourseEntity courseEntity = new CourseEntity(courseDTO);
        courseEntity.setCreateTime(LocalDateTime.now());
        courseEntity.setLastModifyTime(LocalDateTime.now());

        List<Long> cardIdList = courseDTO.getCardListStr();
        Long courseId = courseEntity.getId();
        log.info("courseId:{}", courseId);

        try {
            // 插入课程实体到数据库，首次插入时，自动设置版本号 version = 1
            courseMapper.insert(courseEntity);

            // 插入课程和关联的卡片信息，返回值大于0表示插入成功
            return courseCardMapper.insertCourseAndCards(courseEntity.getId(), cardIdList) > 0;
        } catch (Exception e) {
            // 记录异常信息，并抛出运行时异常
            log.error(e.getMessage());
            throw new RuntimeException("save course failure", e);
        }
    }

    /**
     * 更新课程信息及其关联的卡片信息。
     *
     * @param courseDTO 包含课程更新信息和关联卡片ID列表的课程数据传输对象。
     * @return 返回一个布尔值，如果更新成功则为true，否则为false。
     * @throws RuntimeException 如果更新过程中发生异常，则抛出运行时异常。
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateCourseDTO(CourseDTO courseDTO) {
        log.info("courseDTO的id为"+courseDTO.getId());
        // 将课程DTO转换为实体对象，并设置ID
        CourseEntity courseEntity = new CourseEntity(courseDTO);
        courseEntity.setId(courseDTO.getId());
        log.info("courseEntity的id为"+courseEntity.getId());

        // 设置课程的最后修改时间
        courseEntity.setLastModifyTime(LocalDateTime.now());

        // 获取课程DTO中的卡片ID列表
        List<Long> cardIdList = courseDTO.getCardListStr();

        try {
            // 更新课程实体信息
            courseMapper.updateById(courseEntity);
            // 删除课程关联的所有卡片
            courseCardMapper.deleteAllCardsByCourseId(courseEntity.getId());
            // 插入新的课程和卡片关联关系
            return courseCardMapper.insertCourseAndCards(courseEntity.getId(), cardIdList) > 0;
        } catch (Exception e) {
            log.error(e.getMessage());
            // 如果发生异常，抛出运行时异常
            throw new RuntimeException("update course failure", e);
        }
    }

    @Override
    public List<Long> getAllCourseIds() {
        return courseMapper.getAllCourseIds();
    }

    @Override
    public List<CourseEntity> selectCoursesByKeyword(String keyword) {
        return courseMapper.selectCoursesByKeyword(keyword);
    }
}
