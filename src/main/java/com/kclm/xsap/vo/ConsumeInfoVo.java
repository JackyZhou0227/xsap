package com.kclm.xsap.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author fangkai
 * @description
 * @create 2021-12-20 10:53
 */

@Data
@Accessors
public class ConsumeInfoVo {


    /**
     * 消费记录id
     */
    private Long consumeId;
    /**
     * 消费会员卡id
     */
    private String cardName;
    /**
     * 消费操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd  HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime operateTime;
    /**
     * 当次消费的消耗的次数
     */
    private Integer cardCountChange;
    /**
     * 该会员卡剩余的次数
     */
    private Integer timesRemainder;
    /**
     * 本次消费的金额
     */
    private BigDecimal moneyCostBigD;
    /**
     * 本次消费金额的格式化字符串
     */
    private String moneyCost;
    /**
     * 本次消费操作的类型
     */
    private String operateType;
    /**
     * 操作人
     */
    private String operator;
    /**
     * 消费备注
     */
    private String note;

    /**
     * 消费操作创建时间
     */
    private LocalDateTime createTime;
    /**
     * 消费操作修改时间
     */
    private LocalDateTime lastModifyTime;
}
