package com.kclm.xsap.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanError {
    public static String getErrorData(BindingResult bindingResult) {
        StringBuilder errorDataBuilder = new StringBuilder();
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            String em = fieldError.getDefaultMessage();
            errorDataBuilder.append(em).append("\n");
        }
        return errorDataBuilder.toString().trim();
    }

    public static Map<String, String> getErrorDataMap(BindingResult bindingResult) {
//        List<String> errorDataList = new ArrayList<>();
        Map<String, String> errorDataMap = new HashMap<>();
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            errorDataMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return errorDataMap;
    }
}