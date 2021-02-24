package com.cz.encrypt.demo.constant;

/**
 * @author jiaozi<liaomin @ gvt861.com>
 * @since JDK8
 * Creation time：2019/8/13 17:18
 */
public enum SecurityConstants implements Constant {
    APPID_NULL("220005", "Appid不能为空"),
    VERSION_NULL("220006", "版本不能为空"),
    SIGN_NULL("220007", "签名不能为空"),

    SIGN_ERROR("220002", "签名认证失败"),
    VERSION_ERROR("220003", "版本输入错误"),
    APPID_ERROR("220004", "Appid输入错误");


    private String code;
    private String value;

    private SecurityConstants(String code, String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
