package com.cz.springcloudsdk.error;

import com.cz.springcloudsdk.enums.ErrorCodeEnums;
import lombok.Data;

/**
 * @author alian
 * @date 2020/11/16 上午 11:58
 * @since JDK8
 */
@Data
public class ServiceException extends RuntimeException {

    private String code;

    private String msg;

    public ServiceException(Throwable e) {
        super(e);
        this.code = ErrorCodeEnums.FAIL.getCode();
    }

    public ServiceException(ErrorCodeEnums error, Throwable throwable) {
        this(error.getCode(), error.getMsg(), throwable);
    }

    public ServiceException(String code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(String code, Throwable ex) {
        super(ex);
        this.code = code;
    }

    public ServiceException(String code, String message, Throwable ex) {
        super(message, ex);
        this.code = code;
    }
}
