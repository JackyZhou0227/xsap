package com.kclm.xsap.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author fangkai
 * @description
 * @create 2022-01-08 11:13
 */
@Data
@Accessors(chain = true)
public class ClassCostTempTestVo {
    private Long teacherId;
    private Integer monthValue;
    private Integer totalNumberOfCurrentCourse;

    private Float sumOfCurrentCourseAmount;
}
