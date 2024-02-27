package com.kclm.xsap.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author fangkai
 * @description 老师管理中老师详细信息中的上课记录相关Vo
 * @create 2021-12-14 14:34
 */

@Data
@Accessors(chain = true)
public class TeacherClassRecordVo {

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 上课时间
     */
    private LocalDateTime classTime;

    /**
     * 支持的会员卡名
     */
    private String cardName;

    /**
     * 使用次数
     */
    private Integer timesCost;


}
