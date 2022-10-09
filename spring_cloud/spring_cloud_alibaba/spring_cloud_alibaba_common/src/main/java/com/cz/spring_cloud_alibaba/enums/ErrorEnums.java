package com.cz.spring_cloud_alibaba.enums;

public enum ErrorEnums implements BaseEnums{

    // 3000-3100
    USER_NOT_FOUND(3000, "用户名或密码错误"),
    USER_LOG_ERROR(3001, "登录失败"),
    USER_UN_LOG_ERROR(3002, "用户未登录"),

    SYS_ERROR(9999, "系统内部错误");

    private Integer code;
    private String msg;

    private ErrorEnums(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
