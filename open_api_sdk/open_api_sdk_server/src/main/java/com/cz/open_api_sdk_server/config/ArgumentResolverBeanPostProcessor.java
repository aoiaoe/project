package com.cz.open_api_sdk_server.config;

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

        if (beanName.equals(BEAN_REQUEST_MAPPING_HANDLER_ADAPTER)) {

            RequestMappingHandlerAdapter adapter = (RequestMappingHandlerAdapter) bean;

            List<HandlerMethodArgumentResolver> argumentResolvers = adapter.getArgumentResolvers();

            LinkedList<HandlerMethodArgumentResolver> resolversAdjusted = new LinkedList<>(argumentResolvers);

            argumentResolvers.stream().forEach(r -> {

                if (DemoArgResolver.class.isInstance(r)) {

                    resolversAdjusted.addFirst(r);

                } else {

                    resolversAdjusted.add(r);

                }
            });
            adapter.setArgumentResolvers(resolversAdjusted);

        }
        return bean;
    }

} 