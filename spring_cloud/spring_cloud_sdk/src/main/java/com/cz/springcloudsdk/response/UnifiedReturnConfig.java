package com.cz.springcloudsdk.response;

import com.alibaba.fastjson.JSONObject;
import com.cz.springcloudsdk.IgnoreCommonResponse;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.PostConstruct;

@Configuration
public class UnifiedReturnConfig {

    /**
     * 某一些接口返回的String类型的结果，如果先封装之后再返回，则会报错
     * com.cz.springcloudsdk.response.ResultResponse cannot be cast to java.lang.String
     * 原因是因为，再类型转换器列表中，String类型的转换器排在前面，所以需要配合 WebConfig.java配置类，
     * 将json的序列化起放置在 列表前面
     */
    @RestControllerAdvice(basePackages = "com.cz")
    static class CommonResultResponseAdvice implements ResponseBodyAdvice<Object> {
        @Override
        public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
            return !methodParameter.hasMethodAnnotation(IgnoreCommonResponse.class);
        }

        @Override
        public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                      Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                      ServerHttpResponse response) {

            if (body instanceof ResultResponse) {
                // 兼容旧版本的数据，已经用ResultResponse封装好了的，就不用再进行处理了
                return body;
            } else {
                // 目前只针对POJO的返回对象进行封装
                return new ResultResponse<Object>(body, String.valueOf(HttpStatus.OK.value()), null);
            }
        }

        @PostConstruct
        public void post() {
            System.out.println("------>>>  UnifiedReturnConfig");
        }
    }

    @PostConstruct
    public void post() {
        System.out.println("+++++++++>>>  UnifiedReturnConfig");
    }
}
