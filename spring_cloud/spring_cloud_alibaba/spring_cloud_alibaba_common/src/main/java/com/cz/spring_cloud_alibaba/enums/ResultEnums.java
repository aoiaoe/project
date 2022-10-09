package com.cz.spring_cloud_alibaba.enums;

/**
 * @author jzm
 * @date 2022/10/9 : 13:59
 */
public enum ResultEnums implements BaseEnums{
    SUCCESS(200, "调用成功");

    private Integer code;

    private String msg;

    private ResultEnums(Integer code, String msg){
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
