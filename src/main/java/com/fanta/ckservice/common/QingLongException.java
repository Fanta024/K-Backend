package com.fanta.ckservice.common;

public class QingLongException extends RuntimeException{
    /**
     * 错误码
     */
    private final int code;

    public QingLongException(int code,String message) {
        super(message);
        this.code = code;
    }
    public int getCode() {
        return code;
    }
}
