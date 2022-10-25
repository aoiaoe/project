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
 *
 *  [@FeignClient] 注解的contextId最好配置且唯一，否则如果多个FeignClient使用相同的value或者name，注入时会报错
 *  原因：
 *      FeignClientsRegistrar.registerBeanDefinitions() -> registerFeignClients() ->
 *      String name = getClientName(attributes);方法中获取feignClient的名称优先级
 *          是contextId > value > name > serviceId
 *      所以如果不同FeignClient使用相同的value则会因为有相同的beanName无法注入
 *
 *  解决方案：
 *      1、增加配置，spring.main.allow-bean-definition-overriding: true  允许同名bean覆盖
 *          但是不符合常理，既然你定义了FeignClient就是要使用的，那么覆盖了就没有意义了，删除该类或者合并同名FeignClient即可
 *      2、通过设置@FeignClient注解的注contextId属性，且每一个值全局唯一即可
 *          注入的时候则需要注意根据名称进行注入
 */
@FeignClient(value = "nacos-config-sentinel-order", path = "/order")
public interface OrderFeignClient extends OrderFacade {
}
