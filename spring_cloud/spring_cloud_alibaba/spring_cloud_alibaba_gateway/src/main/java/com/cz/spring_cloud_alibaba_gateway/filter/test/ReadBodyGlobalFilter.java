package com.cz.spring_cloud_alibaba_gateway.filter.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;

/**
 * 此过滤器用于做测试, 将post请求中的请求体读取之后，看是否数据会被清空
 * 当请求被转发到目标服务之后，由于post请求需要请求体进行参数解析，但是数据已经被读取了，会报 HttpMessageNotReadableException异常
 * tips:
 *  做此测试之前，先关闭 BodyCacheGlobalFilter 过滤器, 则会出现异常
 *  然后打开 过滤器，则正常请求
 */
@Slf4j
//@Component
public class ReadBodyGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getRequest().getBody().subscribe(body -> {
            final CharBuffer data = StandardCharsets.UTF_8.decode(body.asByteBuffer());
            log.info("[读取请求body] :{}", data.toString());
            DataBufferUtils.release(body);
        });
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
