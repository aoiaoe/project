package com.cz.encrypt.demo.exception;

import com.cz.encrypt.demo.constant.Constant;

/**
 * @author alian
 * @date 2021/2/24 下午 3:04
 * @since JDK8
 */
public class ServiceException extends RuntimeException {

    private String code;

    public ServiceException(Throwable e) {
        super(e);
        this.code = "fail";
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

    public ServiceException(Constant code) {
        super(code.getValue());
        this.code = code.getCode();
    }

    public ServiceException(Constant code, String message) {
        super(message);
        this.code = code.getCode();
    }

    public ServiceException(Constant code, Throwable ex) {
        this(code);
    }

    public ServiceException(Constant code, String message, Throwable ex) {
        super(message, ex);
        this.code = code.getCode();
    }

    public String getCode() {
        return this.code;
    }
}
