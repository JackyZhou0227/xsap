package com.kclm.xsap.utils.exception;

/**
 * @author weiqing
 * @description
 * @create 2021-10-03 20:22
 */
public enum BizCodeEnumE {

    /**
     *
     */
    UNKNOWN_EXCEPTION(10000,"系统位置异常"),
    VAILD_EXCEPTION(10001,"参数格式校验异常"),
    PRODUCT_UP_EXCEPTION(11000, "商品上架异常");

    private int code;
    private String msg;

    BizCodeEnumE(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
