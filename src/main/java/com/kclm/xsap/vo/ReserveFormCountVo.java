package com.kclm.xsap.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author fangkai
 * @description
 * @create 2021-12-16 16:17
 */

@Data
@Accessors(chain = true)
public class ReserveFormCountVo {

    /**
     * 会员卡id
     */
    private Long cardId;
    /**
     * 默认可用次数
     */
    private Integer cardTotalCount;
    /**
     * 默认可用天数
     */
    private Integer cardTotalDay;


    /**
     * 课程id
     */
    private Long courseId;
    /**
     * 每节课程需花费的次数
     */
    private Integer courseTimesCost;
}
