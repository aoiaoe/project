package com.cz.springcloudconsumerfeignhystrix.config;

import com.cz.springcloud.enums.HttpHeaderEnum;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author alian
 * @date 2020/10/13 下午 2:48
 * @since JDK8
 */
@Configuration
public class FeignInterceptor implements RequestInterceptor {

    @Value("${server.port}")
    private int port;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.getRequestVariables().forEach(System.out::println);
        requestTemplate.header(HttpHeaderEnum.SENDER_CLIENT.name(), "FEIGN_CLIENT_" + port);
    }
}
