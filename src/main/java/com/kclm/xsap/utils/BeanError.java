package com.kclm.xsap.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanError {

    /**
     * 获取验证错误信息
     * @param bindingResult 数据绑定结果，包含表单验证的所有错误信息
     * @return 返回错误信息的字符串，每个错误信息占一行
     */
    public static String getErrorData(BindingResult bindingResult) {
        StringBuilder errorDataBuilder = new StringBuilder();
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            String em = fieldError.getDefaultMessage();
            errorDataBuilder.append(em).append("\n");
        }
        return errorDataBuilder.toString().trim();
    }

    /**
     * 获取验证错误数据的映射表
     *
     * @param bindingResult 包含表单验证结果的对象，能获取到所有的字段错误信息。
     * @return 返回一个包含字段名和错误信息的映射表，方便前端展示错误信息。
     */
    public static Map<String, String> getErrorDataMap(BindingResult bindingResult) {
        Map<String, String> errorDataMap = new HashMap<>();
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            errorDataMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return errorDataMap;
    }
}