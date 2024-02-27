package com.kclm.xsap.vo.register;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author fangkai
 * @description
 * @create 2021-12-30 9:15
 */
@Data
@Accessors(chain = true)
public class RegisterVo {

    //注册用户名
    private String userName;
    //密码
    private String password;

    //第二次输入密码
    private String pwd2;

}
