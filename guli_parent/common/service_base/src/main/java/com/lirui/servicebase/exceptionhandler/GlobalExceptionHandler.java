package com.lirui.servicebase.exceptionhandler;

import com.lirui.commonutils.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ：xxx
 * @description：统一异常处理类
 * @date ：2023/5/6 15:33
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        //打印异常
        e.printStackTrace();
        return R.error().message("执行了全局异常");
    }

    //特殊异常
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e){
        //打印异常
        e.printStackTrace();
        return R.error().message("执行ArithmeticException异常处理");
    }

    //自定义异常
    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public R error(GuliException e){
        //打印异常
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
