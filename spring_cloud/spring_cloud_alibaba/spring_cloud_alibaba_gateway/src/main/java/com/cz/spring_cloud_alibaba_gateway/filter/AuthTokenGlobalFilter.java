package com.cz.spring_cloud_alibaba_gateway.filter;

import com.cz.spring_cloud_alibaba.constants.CommonConstants;
import com.cz.spring_cloud_alibaba.utils.JwtUtils;
import com.cz.spring_cloud_alibaba_gateway.config.AuthProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.PublicKey;
import java.util.*;

/**
 * 全局过滤器:
 *  使用方式: 只需要实现GlobalFilter接口，并添加@Component注解,将其注册到Spring容器，在应用启动时，框架将过滤器添加到全局过滤器链中
 *  使用场景: 权限校验、限流、日志记录
 */
@Component
public class AuthTokenGlobalFilter implements GlobalFilter, AbstractFilteredHandler {

    private final static String TOKEN_HEADER_NAME = "Auth";
    private final static AntPathMatcher matcher = new AntPathMatcher();

    @Autowired
    private AuthProperties authProperties;
    @Autowired
    private PublicKey publicKey;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final ServerHttpRequest request = exchange.getRequest();
        final ServerHttpResponse response = exchange.getResponse();
        final String url = request.getPath().toString();
        if (shouldSkip(url)){
            return chain.filter(exchange);
        }
        final List<String> tokens = request.getHeaders().get(TOKEN_HEADER_NAME);
        final String token = Optional.ofNullable(tokens).orElse(Collections.singletonList(null)).get(0);
        // 对token进行jwt校验
        if(JwtUtils.verify(token, publicKey)){
            return chain.filter(exchange);
        }
        // 如果没有token则返回响应信息
        // 两种方式: 1：直接用response写会
//        return filteredRequest(response, HttpStatus.UNAUTHORIZED, "TOKEN验证失败");
        // 2：抛出异常信息，然后由错误处理器进行错误信息写会
        throw new RuntimeException("TOKEN验证失败");
    }

    private boolean shouldSkip(String url){
        if (CollectionUtils.isEmpty(authProperties.getPermitsUrl())){
            return false;
        }
        return authProperties.getPermitsUrl().stream()
                .allMatch(e -> matcher.match(e, url));
    }

    public static void main(String[] args) {
        System.out.println(new AntPathMatcher().isPattern("/user/{id}"));
        System.out.println(new AntPathMatcher().match("/user/{id}", "/user/1"));
        System.out.println(new AntPathMatcher().match("/user/{id}", "/uses?id=1"));
    }
}
