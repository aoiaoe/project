package com.cz.spring_cloud_alibaba_gateway.filter;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.protocol.HTTP;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufMono;

public interface AbstractFilteredHandler {

    default Mono<Void> filteredRequest(ServerHttpResponse response, HttpStatus httpStatus, String message) {
        response.setStatusCode(httpStatus);
        response.getHeaders().add(HTTP.CONTENT_TYPE, "application/json");
        return response.writeAndFlushWith(Mono.just(ByteBufMono.just(response.bufferFactory().wrap(getWrapData(message)))));
    }

    default byte[] getWrapData(String message) {
        JSONObject json = new JSONObject();
        json.put("code", 500);
        json.put("message", message);
        return json.toJSONString().getBytes();
    }
}
