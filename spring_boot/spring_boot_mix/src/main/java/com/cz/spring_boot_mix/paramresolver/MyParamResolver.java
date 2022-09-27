package com.cz.spring_boot_mix.paramresolver;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;

/**
 * 需要在 WebMvcConfigurer 中添加参数解析器使其生效
 * 自定义参数处理器
 * 参数 通过url参数 pram传递string过来. 然后json化为对象
 *  例子: localhost:18101/test/paramResolver?id=11&name=jzmz  -> ParamConverterDomain.java
 */
@Slf4j
public class MyParamResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 如果想替换掉Spring提供的RequestBody注解,则需要修改此参数解析器的顺序
        // 将其顺序置于官方的解析器前面，否则次解析器会无效
        // 修改解析器顺序， 参考: ArgumentResolverBeanPostProcessor.java
//        return parameter.hasParameterAnnotation(RequestBody.class);

        return parameter.hasParameterAnnotation(MyRequestBody.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        log.info("自定义参数处理器处理参数ing");
        JSONObject json = new JSONObject();
        final Map<String, String[]> parameterMap = webRequest.getParameterMap();
        parameterMap.entrySet().forEach(e -> {
            if(e.getValue() != null && e.getValue()[0] != null) {
                json.put(e.getKey(), e.getValue()[0]);
            }
        });
        Class<?> declaringClass = parameter.getParameterType();
        return json.toJavaObject(declaringClass);
    }
}
