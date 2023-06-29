package com.cz.springcloudconsumerfeign.config;

import com.cz.springcloud.enums.HttpHeaderEnum;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 修改feign请求,如增删header,修改body
 *
 * @author alian
 * @date 2020/10/13 下午 2:48
 * @since JDK8
 */
@Configuration
public class FeignInterceptor implements RequestInterceptor {

    @Value("${server.port:9202}")
    private int port;


    private static final String Auth = "Authorization";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(HttpHeaderEnum.SENDER_CLIENT.name(), "FEIGN_CLIENT_" + port);

        // 传递校验的token
        HttpServletRequest request = (((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())).getRequest();
        if(request.getHeader(Auth) != null) {
            requestTemplate.header(Auth, request.getHeader(Auth));
        }
    }
}
