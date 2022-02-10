package com.cz.spring_boot_security_dy03_in_action_7012.filter;

import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;

/**
 * @author jzm
 * @date 2022/2/10 : 11:13
 */
@Component
public class PermissionFilter extends OncePerRequestFilter{

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        PrintWriter printWriter = response.getWriter();
//        response.setContentType("application/json; charset=UTF-8");
//        response.setCharacterEncoding("utf-8");
//        response.setStatus(HttpStatus.FORBIDDEN.value());
//        printWriter.write("{\"code\":401,\"message\":\"无权限访问\"}");
//        printWriter.flush();
    }

}
