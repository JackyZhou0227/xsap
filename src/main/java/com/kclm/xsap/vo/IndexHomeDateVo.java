package com.kclm.xsap.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author fangkai
 * @description
 * @create 2021-12-06 9:57
 */
@Data
@Accessors(chain = true)
public class IndexHomeDateVo {

    private Integer totalMembers;
    private Integer activeMembers;
    private Integer totalReservations;


    private String[] dateList;
    private Integer[]dailyReservations;
}
