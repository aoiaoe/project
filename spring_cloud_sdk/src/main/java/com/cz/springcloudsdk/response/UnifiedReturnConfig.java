package com.cz.springcloudsdk.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@EnableWebMvc
@Configuration
public class UnifiedReturnConfig {

    @RestControllerAdvice(basePackages = "com.cz")
    static class CommonResultResponseAdvice implements ResponseBodyAdvice<Object> {
        @Override
        public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
            return true;
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
                return new ResultResponse<Object>(body, HttpStatus.OK.value() + "", null);
            }
        }
    }
}
