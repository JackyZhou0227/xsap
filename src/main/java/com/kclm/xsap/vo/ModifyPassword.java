package com.kclm.xsap.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author fangkai
 * @description
 * @create 2022-01-03 18:53
 */
@Data
@Accessors(chain = true)
public class ModifyPassword {

    //员工id
    private Long id;

    //旧密码
    private String oldPwd;

    //新密码
    private String newPwd;

    //第二次输入的新密码
    private String pwd2;
}
