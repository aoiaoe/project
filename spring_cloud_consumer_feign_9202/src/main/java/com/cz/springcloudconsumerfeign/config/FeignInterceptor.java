package com.cz.springcloudconsumerfeign.config;

import com.cz.springcloud.enums.HttpHeaderEnum;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

/**
 * @author alian
 * @date 2020/10/13 下午 2:48
 * @since JDK8
 */
@Configuration
public class FeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(HttpHeaderEnum.SENDER_CLIENT.name(), "FEIGN_CLIENT_9202");
    }
}
