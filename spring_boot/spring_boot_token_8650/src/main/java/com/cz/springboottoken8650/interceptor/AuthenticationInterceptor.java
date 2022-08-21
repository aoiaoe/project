package com.cz.springboottoken8650.interceptor;

import com.cz.springboottoken8650.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final String TOKEN = "token";

    private final JwtUtils jwtUtils;

    @Autowired
    public AuthenticationInterceptor(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // 从 http 请求头中取出 token
        String token = request.getHeader(TOKEN);
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter(TOKEN);
        }

        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        //LoginSkip，有则跳过认证
//        if (method.isAnnotationPresent(LoginSkip.class)) {
//            LoginSkip loginSkip = method.getAnnotation(LoginSkip.class);
//            if (loginSkip.required()) {
//                return true;
//            }
//        }
        //检查有没有需要用户权限的注解
//        if (method.isAnnotationPresent(LoginAuth.class)) {
//            LoginAuth userLogin = method.getAnnotation(LoginAuth.class);
//            if (userLogin.required()) {
//                // 执行认证
//                if (StringUtils.isBlank(token)) {
//                    throw new RuntimeException("无效token，请重新登录");
//                }
//                String username = jwtUtils.getUsername(token);
//                jwtUtils.verify(token, username);
//                return true;
//            }
//        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
