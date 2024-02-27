package com.kclm.xsap.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author fangkai
 * @description
 * @create 2021-12-18 16:44
 */

@Data
@Accessors(chain = true)
public class ClassInfoVo {

    /**
     * 上课记录id
     */
    private Long classRecordId;

    /**
     * 课程名
     */
    private String courseName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime classTime;
    //老师姓名
    private String teacherName;
    //会员卡名
    private String cardName;
    //上课人数
    private Integer classNumbers;
    //单节课单人消耗
    private Integer timesCost;
    //老师评价
    private String comment;
    //上课确认状态
    private Integer checkStatus;
    //课程开始日期
    private LocalDate scheduleStartDate;
    //课程开始时间
    private LocalTime scheduleStartTime;
}
