package com.kclm.xsap.vo.indexStatistics;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author fangkai
 * @description
 * @create 2021-12-29 14:51
 */
@Data
@Accessors(chain = true)
public class IndexPieChartVo {

    private Integer value;
    private String name;
}
