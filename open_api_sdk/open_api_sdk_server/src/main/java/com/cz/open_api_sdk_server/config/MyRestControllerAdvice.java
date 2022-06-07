package com.cz.open_api_sdk_server.config;

import com.alibaba.fastjson.JSONObject;
import com.cz.open_api_sdk_server.response.CommonResponse;
import com.cz.open_api_sdk_server.utils.RSAUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author jzm
 * @date 2022/6/6 : 14:07
 */
@Slf4j
@RestControllerAdvice
public class MyRestControllerAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return AbstractJackson2HttpMessageConverter.class.isAssignableFrom(converterType)
                && (!returnType.getMethod().getReturnType().isAssignableFrom(CommonResponse.class)
                && !returnType.getMethod().getReturnType().isAssignableFrom(ResponseEntity.class));
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        try {
            String s = RSAUtil.privateEncrypt(JSONObject.toJSONString(body), RSAUtil.PRIVATE_KEY);
            return CommonResponse.suc(s);
        } catch (Exception e) {
            log.error("加密出错：", e);
        }
        return CommonResponse.suc(body);
    }
}
