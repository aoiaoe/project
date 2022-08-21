package com.cz.springbootredis.exception;

/**
 * @author jzm
 * @date 2022/5/24 : 14:58
 */
public class ServiceException extends RuntimeException{

    private String message;

    public ServiceException(String message){
        super(message);
    }
}
