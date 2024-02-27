package com.kclm.xsap.vo.statistics;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author fangkai
 * @description
 * @create 2021-12-24 23:20
 */

@Data
@Accessors(chain = true)
public class StatisticsOfCardCostVo {

    /**
     * 统计模式
     */
    private Integer unit;

    /**
     * 要统计的年份【月、季度】
     */
    private Integer yearOfSelect;

    /**
     * 统计开始的年份【年查找模式】
     */
    private Integer beginYear;

    /**
     * 统计结束的年份【年查找模式】
     */
    private Integer endYear;

}
