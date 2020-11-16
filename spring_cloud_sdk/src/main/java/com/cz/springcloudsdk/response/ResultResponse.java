package com.cz.springcloudsdk.response;

import lombok.Data;

/**
 * @author alian
 * @date 2020/11/16 下午 12:03
 * @since JDK8
 */
@Data
public class ResultResponse<T> {

    private T data;
    private String code;
    private String message;

    public ResultResponse(String code){
        this(null, code, null);
    }

    public ResultResponse(String code, String message){
        this(null, code, message);
    }

    public ResultResponse(T data, String code, String message){
        this.data = data;
        this.code = code;
        this.message = message;
    }
}
