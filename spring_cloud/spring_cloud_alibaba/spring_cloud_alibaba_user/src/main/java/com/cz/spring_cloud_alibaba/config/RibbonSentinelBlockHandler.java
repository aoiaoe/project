package com.cz.spring_cloud_alibaba.config;

import com.alibaba.cloud.sentinel.rest.SentinelClientHttpResponse;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.cz.spring_cloud_alibaba.domain.order.OrderVo;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;

@Slf4j
public
class RibbonSentinelBlockHandler{

    public static SentinelClientHttpResponse block(HttpRequest request,
                                                   byte[] body,
                                                   ClientHttpRequestExecution execution,
                                                   BlockException ex){
        log.error("[ribbon 降级] ：path:{}, error:{}", request.getURI().getPath(), ex);

        return new SentinelClientHttpResponse(JSON.toJSONString(ImmutableList.of(new OrderVo(0L, null))));
    }

    public static SentinelClientHttpResponse fallback(HttpRequest request,
                                                   byte[] body,
                                                   ClientHttpRequestExecution execution,
                                                   BlockException ex){
        log.error("[ribbon 降级] ：path:{}, error:{}", request.getURI().getPath(), ex);

        return new SentinelClientHttpResponse(JSON.toJSONString(ImmutableList.of(new OrderVo(0L, null))));
    }
}