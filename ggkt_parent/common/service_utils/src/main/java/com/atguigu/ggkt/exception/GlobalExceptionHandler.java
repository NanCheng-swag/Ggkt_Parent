package com.atguigu.ggkt.exception;

import com.atguigu.ggkt.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author nancheng
 * @version 1.0
 * @date 2022/10/23 20:12
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.fail(null).message("全局登陆异常！");
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result error(ArithmeticException e) {
        e.printStackTrace();
        return Result.fail(null).message("执行了特定异常！");
    }

    @ExceptionHandler(GgktException.class)
    @ResponseBody
    public Result error(GgktException e) {
        e.printStackTrace();
        return Result.fail(null).code(e.getCode()).message(e.getMsg());

    }
}
