package com.cz.spring_cloud_alibaba.advice;

import com.cz.spring_cloud_alibaba.anntation.IgnoreResponseBody;
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
 * @author jzm
 * @date 2022/10/9 : 11:28
 */
@RestControllerAdvice(basePackages = "com.cz.spring_cloud_alibaba")
public class CommonResponseControllerAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> clazz) {
        if (returnType.getDeclaringClass().isAnnotationPresent(IgnoreResponseBody.class)
                || returnType.getMethod().isAnnotationPresent(IgnoreResponseBody.class)) {
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
