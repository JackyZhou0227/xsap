package com.kclm.xsap.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author fangkai
 * @description
 * @create 2021-12-11 16:08
 */

@Data
@Accessors(chain = true)
public class CourseLimitVo {

    /**
     * 限制性别
     */
    private String limitSex;
    /**
     * 限制年龄
     */
    private Integer limitAge;

    /**
     * 课程时长
     */
    private Long duration;
}
