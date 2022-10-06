package com.cz.spring_cloud_alibaba_gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

/**
 * 演示自定义gateway的局部过滤器
 * 作用: 过滤请求头中有指定值的请求
 * 使用方式:
 *  1、以继承AbstractGatewayFilterFactory工厂类的方式编写过滤器, 必须以前缀 + GatewayFilterFactory方式命名
 *      然后在配置路由时，则过滤器名称就为前缀, 例如此类， 则需配置为
 *      {
            "name": "HeaderToken",
            "args": {
                "header": "gw-header"
                 }
        }
        注意： 需要将过滤器添加@Component注解，将实例注册到Spring
 *
 */
@Component
public class HeaderTokenGatewayFilterFactory
        extends AbstractGatewayFilterFactory<HeaderTokenGatewayFilterFactory.Config>
        implements Ordered, AbstractFilteredHandler {

    private static final int ORDER = 10000;
    private static final String HEADER_NAME = "HEADER";
    private static final String FILTERED_MSG = "无特定请求头，权限不足";

    public HeaderTokenGatewayFilterFactory(){
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList(HEADER_NAME);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return new GatewayFilter(){
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                final ServerHttpRequest request = exchange.getRequest();
                final ServerHttpResponse response = exchange.getResponse();
                final boolean b = request.getHeaders().containsKey(config.getHeader());
                if(b){
                    return chain.filter(exchange);
                }

                // 直接返回 没有响应值，但是对于客户端来说不友好
//               return response.setComplete().then();
                //设置返回的数据（非json格式）
//                response.setStatusCode(HttpStatus.UNAUTHORIZED);
//                return response.writeWith(Flux.just(response.bufferFactory().wrap(FILTERED_MSG.getBytes())));
                return filteredRequest(response, HttpStatus.UNAUTHORIZED, FILTERED_MSG);
            }
        };
    }

    @Override
    public int getOrder() {
        return ORDER;
    }

    public static class Config{

        private String header;

        public void setHeader(String header) {
            this.header = header;
        }

        public String getHeader() {
            return header;
        }
    }
}
