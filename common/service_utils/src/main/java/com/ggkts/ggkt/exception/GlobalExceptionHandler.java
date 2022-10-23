package com.ggkts.ggkt.exception;

import com.ggkts.ggkt.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice  //aop  只做增强
@ResponseBody
public class GlobalExceptionHandler {   //异常处理  分为全局异常处理（对所有异常进行处理） 特定异常处理（对特定异常进行处理）  自定义异常处理（对自己定义异常进行处理）

    //全局异常处理
    @ExceptionHandler(Exception.class)
    public Result error(Exception e) {
        System.out.println("全局.....");
        e.printStackTrace();
        return Result.fail(null).message("执行全局异常处理");
    }

    //特定异常处理ArithmeticException
    @ExceptionHandler(ArithmeticException.class)
    public Result error(ArithmeticException e) {
        System.out.println("特定.....");
        e.printStackTrace();
        return Result.fail(null).message("执行ArithmeticException异常处理");
    }

    //自定义异常处理GgktException
    public Result error(GgktException ex){

        Result<Object> fail = Result.fail(null);
        fail.setCode(ex.getCode());
        return Result.fail(null).code(ex.getCode()).message(ex.getMsg());
    }

}
