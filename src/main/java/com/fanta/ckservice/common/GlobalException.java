package com.fanta.ckservice.common;

import com.fanta.ckservice.utils.BaseResponse;
import com.fanta.ckservice.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalException {
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?>  runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException:{}",e.getMessage());
        return ResultUtils.error(500,e.getMessage());
    }
}
