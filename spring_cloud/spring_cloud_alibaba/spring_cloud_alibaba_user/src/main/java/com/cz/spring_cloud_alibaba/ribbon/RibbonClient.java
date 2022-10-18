package com.cz.spring_cloud_alibaba.ribbon;

import com.cz.spring_cloud_alibaba.domain.order.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * sentinel增强resttemplate实现降级
 * 参考 RibbonConfig.java
 * @author jzm
 * @date 2022/10/18 : 09:29
 */
@Service
public class RibbonClient {

    /**
     * 此客户端已经使用sentinel进行增强
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 做测试
     * 1：流控降级：
     *  针对的此工程(nacos-config-sentinel-user)的http://nacos-config-sentinel-order/order/userOrders链路资源
     *  而非nacos-config-sentinel-order工程中的资源
     * 2、容错降级：对于服务不可用时无效
     * @param userId
     * @return
     */
    public List<OrderVo> userOrders(Long userId) {
        String url = String.format("http://nacos-config-sentinel-order/order/userOrders?userId=%s", userId);
        ResponseEntity<List<OrderVo>> exchange = this.restTemplate.exchange(url,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<OrderVo>>() {
                });
        return exchange.getBody();
    }
}
