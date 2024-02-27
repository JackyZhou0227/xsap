package com.kclm.xsap.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kclm.xsap.entity.EmployeeEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author fangkai
 * @description
 * @create 2021-12-13 17:22
 */

@Data
@Accessors(chain = true)
public class ScheduleDetailReservedVo {
    /**
     * 预约状态，1有效，0无效
     */
    private Integer reserveStatus;
    /**
     * 上课确认状态
     */
    private Integer classCheck;
    /**
     * 会员名
     */
    private String memberName;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 使用的会员卡
     */
    private String cardName;
    /**
     * 预约人数
     */
    private Integer reserveNumbers;
    /**
     * 使用次数
     */
    private Integer timesCost;
    /**
     *操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime operateTime;
    /**
     * 操作人
     */
    private String operator;
    /**
     * 预约来源
     */
    private String comment;
    /**
     * 预约备注
     */
    private String reserveNote;

    /**
     * 预约id
     */
    private Long reserveId;

    private LocalDateTime createTime;
    private LocalDateTime lastModifyTime;


    //上课时间
    private LocalDate startDate;
    private LocalTime classTime;


}
