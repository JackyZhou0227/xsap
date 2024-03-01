package com.kclm.xsap.service;

import com.kclm.xsap.entity.CourseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CourseService {
    CourseEntity getById(Long courseId);
}
