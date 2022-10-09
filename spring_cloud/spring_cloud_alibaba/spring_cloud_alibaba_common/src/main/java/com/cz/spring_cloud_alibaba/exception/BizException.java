package com.cz.spring_cloud_alibaba.exception;

import com.cz.spring_cloud_alibaba.enums.ErrorEnums;
import lombok.Data;

/**
 * @author jzm
 * @date 2022/10/9 : 13:48
 */
@Data
public class BizException extends RuntimeException{
    private Integer code;

    private String msg;

    public BizException(Integer code, String message){
        super();
        this.code = code;
        this.msg =message;
    }
    public BizException (ErrorEnums error){
        super();
        this.code = error.getCode();
        this.msg = error.getMsg();
    }

    public static BizException error(ErrorEnums error){
        return new BizException(error);
    }
}
