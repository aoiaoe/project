package com.cz.spring_boot_template.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jzm
 * @date 2022/7/12 : 15:16
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static final String LOGIN_URL = "/freemarker/login";
    private static final String METHOD = "get";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        if(LOGIN_URL.equals(requestURI) && METHOD.equalsIgnoreCase(method)){
            return true;
        }
        // 从redis中获取token
        String token = request.getParameter("token");
        if(StringUtils.isEmpty(token) || !"tokenaaa".equalsIgnoreCase(token)){
            // token 错误
            // 不符合条件的给出提示信息，并转发到登录页面
            String redirecturl = request.getContextPath() + "/freemarker/login";
            response.sendRedirect(redirecturl);
            return false;
        }
        return false;
    }
}
