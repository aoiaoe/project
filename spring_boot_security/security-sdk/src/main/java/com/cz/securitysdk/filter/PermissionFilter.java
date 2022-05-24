package com.cz.securitysdk.filter;

import com.alibaba.fastjson.JSON;
import com.cz.securitysdk.vo.AjaxResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author jzm
 * @date 2022/2/10 : 11:13
 */
@Component
public class PermissionFilter extends OncePerRequestFilter{

    @Autowired
    private RedisTemplate stringObjectRedisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        PrintWriter printWriter = response.getWriter();
//        response.setContentType("application/json; charset=UTF-8");
//        response.setCharacterEncoding("utf-8");
//        response.setStatus(HttpStatus.FORBIDDEN.value());
//        printWriter.write("{\"code\":401,\"message\":\"无权限访问\"}");
//        printWriter.flush();
        if(stringObjectRedisTemplate.opsForValue().get("key") != null){
            filterChain.doFilter(request, response);
        } else {
            AjaxResponseBody responseBody = new AjaxResponseBody();

            responseBody.setStatus("000");
            responseBody.setMsg("无权限");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(responseBody));
        }

    }

}
