package com.kclm.xsap.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author fangkai
 * @description
 * @create 2022-01-06 15:29
 */
@Data
@Accessors(chain = true)
public class ScheduleForConsumeSearchVo {

    //排课id
    private Long scheduleId;

    //课程名
    private String courseName;

    //老师名
    private String teacherName;

    //上课时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime classDateTime;


}
