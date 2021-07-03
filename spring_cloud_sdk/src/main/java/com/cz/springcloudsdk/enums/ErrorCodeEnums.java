package com.cz.springcloudsdk.enums;

/**
 * @author alian
 * @date 2020/11/16 下午 12:01
 * @since JDK8
 */
public enum ErrorCodeEnums {

    FAIL("0000", "失败"),
    PARAM_ERROR("0001", "参数错误"),
    ALG_ERROR("0002", "算法错误");

    private String code;

    private String msg;

    ErrorCodeEnums(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}
