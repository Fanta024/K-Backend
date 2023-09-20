package com.fanta.ckservice.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CodeEnum {
    SYSTEM_ERROR(500, "系统错误"),
    DUPLICATE_ERROR(5001, "cookie已存在");
    private Integer code;
    private String msg;

}
