package com.kclm.xsap.vo.statistics;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author fangkai
 * @description
 * @create 2021-12-23 15:13
 */
@Data
@Accessors(chain = true)
public class CardCostVo {
    //收费模式
    private String title;
    //x轴名字
    private String xname;
    //x轴数据
    private List<String> time;
    //y轴数据
    private List<Integer> data;

    private List<Integer> data2;

}
