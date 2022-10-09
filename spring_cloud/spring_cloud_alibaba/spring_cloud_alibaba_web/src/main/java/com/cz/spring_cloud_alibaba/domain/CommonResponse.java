package com.cz.spring_cloud_alibaba.domain;

import com.cz.spring_cloud_alibaba.enums.ResultEnums;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author jzm
 * @date 2022/10/9 : 11:32
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommonResponse<T> implements Serializable {

    private Integer code;

    private String message;

    private T data;

    public CommonResponse(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public static <T> CommonResponse suc(ResultEnums result, T data){
        return new CommonResponse(result.getCode(), result.getMsg(), data);
    }

    public static CommonResponse error(Integer code, String msg) {
        return new CommonResponse(code, msg);
    }
}
