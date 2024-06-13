package com.jzm.common.util;

public enum ErrorCode {

    LOGIN_FIRST(10001, "Login First, Please!"),
    LOGIN_PARAM(10002, "Login param error!"),
    ;

    private int code;
    private String msg;

    private ErrorCode(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
