package com.kclm.xsap.utils.exception;

import com.kclm.xsap.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fangkai
 * @description
 * @create 2021-12-22 14:54
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionControllerAdvice {

    @ExceptionHandler(value= {MethodArgumentNotValidException.class , BindException.class})
    @ResponseBody
    public R handleVaildException(Exception e){
        BindingResult bindingResult = null;
        if (e instanceof MethodArgumentNotValidException) {
            bindingResult = ((MethodArgumentNotValidException)e).getBindingResult();
        } else if (e instanceof BindException) {
            bindingResult = ((BindException)e).getBindingResult();
        }
        Map<String,String> errorMap = new HashMap<>(16);
        bindingResult.getFieldErrors().forEach((fieldError)->
                errorMap.put(fieldError.getField(),fieldError.getDefaultMessage())
        );
        return R.error(444, "非法参数..").put("errorMap", errorMap);
//        return R.(400 , "非法参数 !" , errorMap);
    }

    /**
     * 自定义异常处理器
     */
//    @ControllerAdvice//用于不返回json数据
    public class CustomExtHandler {

        @ExceptionHandler(value=Exception.class)//处理哪一类异常
        Object handlerException(Exception e,  HttpServletRequest request){
            ModelAndView modelAndView = new ModelAndView();
            //跳转异常页面路径
            modelAndView.setViewName("error/404");
            //页面显示错误信息  页面只需要使用对应的取值方式取值就可以取到msg了
            modelAndView.addObject("msg",e.getMessage());
            return modelAndView;
        }
    }



}
