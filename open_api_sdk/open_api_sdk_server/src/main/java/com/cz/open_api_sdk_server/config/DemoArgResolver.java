package com.cz.open_api_sdk_server.config;

import com.alibaba.fastjson.JSONObject;
import com.cz.open_api_sdk_server.utils.ParamHolder;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class DemoArgResolver implements HandlerMethodArgumentResolver {
   @Override
   public boolean supportsParameter(MethodParameter parameter) {
      System.out.println("support");
      return parameter.hasParameterAnnotation(RequestBody.class);
   }

   @Override
   public Object resolveArgument(MethodParameter parameter,
                          ModelAndViewContainer mavContainer,
                          NativeWebRequest webRequest,
                          WebDataBinderFactory binderFactory) throws Exception {
      System.out.println("resolveArgument " + parameter.getParameterName());
      Class<?> declaringClass = parameter.getParameterType();
      return JSONObject.parseObject(ParamHolder.get(), declaringClass);
   }
}
