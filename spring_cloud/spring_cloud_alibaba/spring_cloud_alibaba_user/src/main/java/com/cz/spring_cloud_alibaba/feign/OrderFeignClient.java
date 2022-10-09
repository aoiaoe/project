package com.cz.spring_cloud_alibaba.feign;

import com.cz.spring_cloud_alibaba_api.facade.order.OrderFacade;
import org.springframework.cloud.openfeign.FeignClient;


/**
 * 实现feign客户端一种实践方式: 参考dubbo
 *  抽象出门面接口,
 *  提供者实现此门面接口, 即可对外提供rest服务,
 *  消费者继承此门面接口, 增加@FeignClient注解，通过配置中心即可轻易的实现feign客户端
 *
 *  注意@FeignClient有坑，服务名中不允许有下划线_
 */
@FeignClient(value = "nacos-config-sentinel-order", path = "/order")
public interface OrderFeignClient extends OrderFacade {
}
