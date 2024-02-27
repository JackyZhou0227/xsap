package com.kclm.xsap.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author fangkai
 * @description
 * @create 2021-12-24 17:31
 */
@Data
@Accessors(chain = true)
public class MemberCardStatisticsWithTotalDataInfoVo {

    private List<MemberCardStatisticsVo> memberCardStatisticsVos;

    /**
     * 总课时
     */
    private Integer totalCourseTimeAll;
    /**
     * 已用课时
     */
    private Integer usedCourseTimeAll;

    /**
     * 剩余课时
     */
    private Integer remainCourseTimeAll;
    /**
     * 总金额
     */
    private BigDecimal totalMoneyAll;
    /**
     * 已用金额
     */
    private BigDecimal usedMoneyAll;
    /**
     * 剩余金额
     */
    private BigDecimal remainMoneyAll;

}
