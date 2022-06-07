package com.cz.open_api_sdk_server.config;

import java.util.LinkedList;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**

 * 将 AuthenticationPrincipalArgumentResolver 移到最前面，如果不做调整，

 * 会因为ProxyingHandlerMethodArgumentResolver在AuthenticationPrincipalArgumentResolver前面，

 * 导致 @AuthenticationPrincipal Account account 无法注入

 */
@Component
public class ArgumentResolverBeanPostProcessor implements BeanPostProcessor {

    private static final String BEAN_REQUEST_MAPPING_HANDLER_ADAPTER = "requestMappingHandlerAdapter";

    @Override

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if (beanName.equals(BEAN_REQUEST_MAPPING_HANDLER_ADAPTER)) {

            RequestMappingHandlerAdapter adapter = (RequestMappingHandlerAdapter)bean;

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