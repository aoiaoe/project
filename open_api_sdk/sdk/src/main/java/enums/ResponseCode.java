package enums;

import lombok.Getter;

@Getter
public enum ResponseCode {

    REQUEST_SUC(200, "请求成功"),

    API_ERROR(10000, "请求错误"),
    API_CRYPT_ERROR(10001, "数据加密错误"),
    API_DECRYPT_ERROR(10002, "数据解密错误"),

    SYSTEM_ERROR(20000, "系统错误"),

    ;

    private Integer code;

    private String msg;

    ResponseCode(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

}
