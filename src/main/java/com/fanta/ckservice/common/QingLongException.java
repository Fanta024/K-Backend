package com.fanta.ckservice.common;

public class QingLongException extends RuntimeException{
    /**
     * 错误码
     */
    private final int code;

    public QingLongException(CodeEnum codeEnum, String message) {
        super(message);
        this.code = codeEnum.getCode();
    }

    public int getCode() {
        return code;
    }
}
