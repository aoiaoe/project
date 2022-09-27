package com.cz.spring_boot_mix.paramresolver;

import java.util.LinkedList;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * 因为需要使用自定义的参数解析器，并且解析@RequestBody注解的参数
 * 所以需要将自己的参数解析至于参数解析器链的前面，否则会走官方解析器，从而自定义解析器无效
 */
@Component
public class ArgumentResolverBeanPostProcessor implements BeanPostProcessor {

    private static final String BEAN_REQUEST_MAPPING_HANDLER_ADAPTER = "requestMappingHandlerAdapter";

    @Override

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        // 获取请求处理器适配器
        if (beanName.equals(BEAN_REQUEST_MAPPING_HANDLER_ADAPTER)) {

            RequestMappingHandlerAdapter adapter = (RequestMappingHandlerAdapter) bean;

            // 获取参数解析器链
            List<HandlerMethodArgumentResolver> argumentResolvers = adapter.getArgumentResolvers();

            // 生成新链
            LinkedList<HandlerMethodArgumentResolver> resolversAdjusted = new LinkedList<>(argumentResolvers);

            argumentResolvers.stream().forEach(r -> {

                // 如果是自定义参数解析器，则加到链的最前面
                if (MyParamResolver.class.isInstance(r)) {

                    resolversAdjusted.addFirst(r);

                } else {

                    resolversAdjusted.add(r);

                }
            });
            // 给请求处理器适配器设置重排序后的参数解析器链
            adapter.setArgumentResolvers(resolversAdjusted);

        }
        return bean;
    }

} 