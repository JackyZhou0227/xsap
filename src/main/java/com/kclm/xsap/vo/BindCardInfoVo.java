package com.kclm.xsap.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author fangkai
 * @description
 * @create 2021-12-23 16:57
 */
@Data
@Accessors(chain = true)
public class BindCardInfoVo {
    //绑定id
    private Long id;


    //会员卡名
    private String name;

}
