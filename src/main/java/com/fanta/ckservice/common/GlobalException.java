package com.fanta.ckservice.common;

import com.fanta.ckservice.utils.BaseResponse;
import com.fanta.ckservice.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@RestControllerAdvice
@Slf4j
public class GlobalException {
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException:{}", e.getMessage());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        e.printStackTrace(new PrintStream(baos));
        String exception = baos.toString();
        log.error("错误日志:{}", exception);
        return ResultUtils.error(500, e.getMessage());
    }

    @ExceptionHandler(QingLongException.class)
    public BaseResponse<?> qingLongExceptionHandler(QingLongException e) {
        log.error("QingLongException:{}", e.getMessage());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        e.printStackTrace(new PrintStream(baos));
        String exception = baos.toString();
        log.error("错误日志:{}", exception);
        return ResultUtils.error(e.getCode(), e.getMessage());
    }
}
