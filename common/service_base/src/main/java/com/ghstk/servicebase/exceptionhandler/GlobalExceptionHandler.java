package com.ghstk.servicebase.exceptionhandler;


import com.ghstk.commonutils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.ng().message("全局异常处理器: 出错了");
    }

    @ExceptionHandler(MyException.class)
    @ResponseBody
    public Result error(MyException e) {
        log.error(e.getMsg());
        e.printStackTrace();
        return Result.ng().code(e.getCode()).message(e.getMsg());
    }

}
