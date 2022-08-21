package com.cz.securitysdk.filter;

import com.cz.securitysdk.entity.LoginUser;
import com.cz.securitysdk.config.TokenProperties;
import com.cz.securitysdk.config.TokenService;
import com.cz.securitysdk.context.LoginUserContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

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

    /**
     * 过滤之前或调用重写的shouldNotFilter()方法判断是否需要本过滤器过滤
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(request);
        if(!StringUtils.isEmpty(token)){
            // 使用jwt中存储的redisToken去redis中获取用户对象
            LoginUser loginUser = tokenService.getLoginUser(token);
            if(loginUser == null){
                response.sendError(HttpStatus.FORBIDDEN.value(), "用户未登录");
                return;
            } else {
                LoginUserContextHolder.setContext(loginUser);
                // 使用获取到的用户对象构造用于下一步UsernamePasswordAuthenticationFilter过滤器验证的用户Token对象
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword(), loginUser.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // 将构造出来的用户token对象放入Security上下文中,交由UsernamePasswordAuthenticationFilter过滤器进行验证
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        // 继续过滤
        filterChain.doFilter(request, response);

        // 请求完成之后清除线程
        LoginUserContextHolder.clear();
    }

    private String getToken(HttpServletRequest request){
        return request.getHeader(tokenProperties.getHeaderName());
    }

    /**
     * 设置不需要过滤的路径
     * @param request
     * @return
     * @throws ServletException
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return tokenProperties.getIgnoreUrls().stream()
                .anyMatch(e -> new AntPathMatcher().match(e, request.getServletPath()));
    }
}
