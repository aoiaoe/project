package com.cz.spring_cloud_alibaba.advice;

import com.cz.spring_cloud_alibaba.domain.CommonResponse;
import com.cz.spring_cloud_alibaba.enums.ErrorEnums;
import com.cz.spring_cloud_alibaba.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jzm
 * @date 2022/10/9 : 14:03
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice{

    @ExceptionHandler(value = BizException.class)
    public CommonResponse bizExceptionHandler(HttpServletRequest request, BizException ex){
        log.error("[业务异常] ： [{}], ", ex.getMessage(), ex);
        return CommonResponse.error(ex.getCode(), ex.getMsg());
    }

//    @ExceptionHandler(value = Exception.class)
//    public CommonResponse exceptionHandler(HttpServletRequest request, Exception ex){
//        log.error("[系统异常] ： [{}], ", ex.getMessage(), ex);
//        return CommonResponse.error(ErrorEnums.SYS_ERROR.getCode(), ex.getMessage());
//    }
}
