package com.cz.springcloudsdk.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author alian
 * @date 2020/10/13 下午 3:15
 * @since JDK8
 */
public class FeignHeaderInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String sender_client = request.getHeader("SENDER_CLIENT");
        System.out.println("SENDER_CLIENT ============>" + sender_client);
        // 指定9203端口服务才拦截错误
        if (sender_client != null && sender_client.equals("FEIGN_CLIENT_9203")) {
            throw new RuntimeException("SDK FEIGN HEADER 拦截器报错: 参数错误");
        }
        return true;
    }
}
