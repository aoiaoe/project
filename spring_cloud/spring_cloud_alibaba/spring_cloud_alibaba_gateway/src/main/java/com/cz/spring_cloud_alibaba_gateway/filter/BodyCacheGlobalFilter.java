package com.cz.spring_cloud_alibaba_gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 将请求数据缓存下来之后，后续过滤器就可以多次获取请求体中的数据
 *  原因: 因为读取时数据流指针的单向移动导致请求的body内容只可读取一次。
 */
@Component
public class BodyCacheGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if(exchange.getRequest().getMethod() == HttpMethod.POST) {
            return DataBufferUtils.join(exchange.getRequest().getBody()).flatMap(data -> {
                // 确保数据缓冲区不被释放
                DataBufferUtils.retain(data);
                // defer just 都是去创建数据源, 得到当前数据源的副本
                Flux<DataBuffer> cachedFlux = Flux.defer(() ->
                        Flux.just(data.slice(0, data.readableByteCount())));

                // 重新包装 ServerHttpRequest, 重写getBody方法， 能够返回请求数据
                ServerHttpRequest request = new ServerHttpRequestDecorator(exchange.getRequest()) {
                    @Override
                    public Flux<DataBuffer> getBody() {
                        return cachedFlux;
                    }
                };
                // 将包装后的request继续向下传递
                return chain.filter(exchange.mutate().request(request).build());
            });
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
}
