package com.kclm.xsap.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CardTipVo {

    private Integer cardTotalCount;

    private Integer courseTimesCost;

}
