package com.kclm.xsap.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author fangkai
 * @description
 * @create 2021-12-23 9:33
 *
 */

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class MemberCardStatisticsVo {

    //会员id
    private Long memberId;

    //会员姓名
    private String memberName;

    //会员卡号(一个会员可以有多张卡)
    private Long bindCardId;

    //总次数
    private Integer totalClassTimes;

    //已用课次
    private Integer usedClassTimes;

    //剩余课次
    private  Integer remainingClassTimes;

    //总金额
    private BigDecimal lumpSumBigD;
    private String lumpSum;

    //已用金额
    private BigDecimal amountUsedBigD;
    private String amountUsed;

    //剩余金额
    private BigDecimal balanceBigD;
    private String balance;

}
