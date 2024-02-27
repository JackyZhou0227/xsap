package com.kclm.xsap.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author fangkai
 * @description
 * @create 2021-12-18 11:38
 */
@Data
@Accessors(chain = true)
public class MemberDetailReservedVo {

    /**
     * 预约id
     */
    private Long reserveId;
    /**
     * 预约的课程名
     */
    private String courseName;
    /**
     * 预约的课程时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private LocalDateTime reserveTime;
    /**
     * 预约使用的会员卡
     */
    private String cardName;
    /**
     * 此条预约记录的预约人数
     */
    private Integer reserveNumbers;
    /**
     * 使用次数
     */
    private Integer timesCost;
    /**
     * 操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private LocalDateTime operateTime;
    /**
     * 操作人
     */
    private String operator;
    /**
     * 预约备注
     */
    private String reserveNote;
    /**
     * 预约类型
     */
    private Integer reserveStatus;

}
