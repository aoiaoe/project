package com.cz.spring_boot_security_dy03_in_action_7012.filter;

import com.cz.spring_boot_security_dy03_in_action_7012.config.TokenProperties;
import com.cz.spring_boot_security_dy03_in_action_7012.config.TokenService;
import com.cz.spring_boot_security_dy03_in_action_7012.context.LoginUserContextHolder;
import com.cz.spring_boot_security_dy03_in_action_7012.entity.LoginUser;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.context.support.SecurityWebApplicationContextUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import sun.plugin.liveconnect.SecurityContextHelper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private TokenProperties tokenProperties;
    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(request);
        // 使用jwt中存储的redisToken去redis中获取用户对象
        LoginUser loginUser = tokenService.getLoginUser(token);
        if(loginUser == null){
            response.sendError(HttpStatus.FORBIDDEN.value(), "用户未登录");
        }
        LoginUserContextHolder.setContext(loginUser);
        // 使用获取到的用户对象构造用于下一步UsernamePasswordAuthenticationFilter过滤器验证的用户Token对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword(), loginUser.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        // 将构造出来的用户token对象放入Security上下文中
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 继续过滤
        filterChain.doFilter(request, response);

        // 请求完成之后清除线程
        LoginUserContextHolder.clear();
    }

    private String getToken(HttpServletRequest request){
        return request.getHeader(tokenProperties.getHeaderName());
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return tokenProperties.getIgnoreUrls().stream()
                .anyMatch(e -> new AntPathMatcher().match(e, request.getServletPath()));
    }
}
