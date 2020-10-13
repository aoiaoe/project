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
        return true;
    }
}
