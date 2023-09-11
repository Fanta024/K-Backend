package com.fanta.ckservice.utils;

public class ResultUtils {
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(200, data, "成功");
    }
    public static <T> BaseResponse<T> success() {
        return new BaseResponse<>(200, "成功");
    }
    public static <T> BaseResponse<T> error(Integer code, String msg) {
        return new BaseResponse<>(code, msg);
    }

}
