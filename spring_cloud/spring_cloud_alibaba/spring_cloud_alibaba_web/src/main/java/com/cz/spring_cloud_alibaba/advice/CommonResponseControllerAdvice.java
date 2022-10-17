package com.cz.spring_cloud_alibaba.advice;

import com.cz.spring_cloud_alibaba.anntation.IgnoreCommonResponseBody;
import com.cz.spring_cloud_alibaba.domain.CommonResponse;
import com.cz.spring_cloud_alibaba.enums.ResultEnums;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 需要注意的是，有一些类不能序列化成json， 使用统一响应会出问题，
 * 比如string，如果方法返回值为String， 要么添加@IgnoreCommonResponseBody注解，取消统一响应
 *  要么使用Pojo类进行返回
 * @author jzm
 * @date 2022/10/9 : 11:28
 */
@RestControllerAdvice(basePackages = "com.cz.spring_cloud_alibaba")
public class CommonResponseControllerAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> clazz) {
        if (returnType.getDeclaringClass().isAnnotationPresent(IgnoreCommonResponseBody.class)
                || returnType.getMethod().isAnnotationPresent(IgnoreCommonResponseBody.class)) {
            return false;
        }
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        if(body instanceof CommonResponse){
            return body;
        }
        return CommonResponse.suc(ResultEnums.SUCCESS, body);
    }
}
