package com.fanta.ckservice.utils;

import lombok.Data;

@Data
public class BaseResponse<T> {
    private Integer code;
    private T data;
    private String message;

    public BaseResponse(Integer code, T data, String message) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public BaseResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
