package com.kclm.xsap.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kclm.xsap.model.dto.CourseDTO;
import com.kclm.xsap.model.entity.CourseEntity;
import org.springframework.stereotype.Service;

public interface CourseService extends IService<CourseEntity> {

    boolean isCourseNameExist(String name);

    boolean saveCourseDTO(CourseDTO courseDTO);
}
