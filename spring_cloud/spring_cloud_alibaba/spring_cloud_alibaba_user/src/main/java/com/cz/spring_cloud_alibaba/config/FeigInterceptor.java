package com.cz.spring_cloud_alibaba.config;

import com.alibaba.cloud.commons.lang.StringUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.seata.core.context.RootContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * feign拦截器，可以修改请求头和请求体
 */
@Configuration
public class FeigInterceptor implements RequestInterceptor {

    @Value("${spring.application.name}")
    private String name;
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("client", name);
        String xid = RootContext.getXID();
        System.out.println("----FeignConfig first get xid ----" + xid);
        if (StringUtils.isNotEmpty(xid)) {
            System.out.println("----FeignConfig second get xid ----" + xid);
            requestTemplate.header(RootContext.KEY_XID, xid);
        }
    }
}
