package com.kclm.xsap.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author fangkai
 * @description
 * @create 2021-12-20 17:32
 */

@Data
@Accessors
public class OperateRecordVo {
    /**
     * 操作记录id
     */
    private Long id;
    /**
     * 操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd  HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime operateTime;
    /**
     * 操作类型
     */
    private String operateType;
    /**
     * 消费的卡次数
     */
    private Integer cardCountChange;
    /**
     * 充值的卡次数
     */
    private Integer addCount;

    /**
     * 变化的次数
     */
    private Integer changeCount;

    /**
     * 有效期
     */
//    private Integer validDay;
    /**
     * 卡到期日
     */
//    private LocalDateTime endToDate;
    /**
     * 消费金额
     */
//    @JSONField(format = "¤00.00")
//    @JsonFormat(pattern = "¤00.00")
    private BigDecimal moneyCost;
    /**
     * 充值金额
     */
//    @JsonFormat(pattern = "¤00.00")
//    @JSONField(format = "¤00.00")
    private BigDecimal receivedMoney;
    /**
     * 变化的金额
     */
    private String changeMoney;

    /**
     * 操作人
     */
    private String operator;
    /**
     * 备注
     */
    private String cardNote;
    /**
     * 状态
     */
    private Integer status;

    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd  HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd  HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime lastModifyTime;
}
