package com.kclm.xsap.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author fangkai
 * @description
 * @create 2021-12-10 11:29
 */

@Data
@Accessors(chain = true)
public class SearchMemberToBindVo {

    /**
     * 会员id
     */
    private Long id;
    /**
     * 会员名
     */
    private String name;
    /**
     * 会员性别
     */
    private String sex;
    /**
     * 会员电话号码
     */
    private String phone;


}
