package com.cz.springcloudconsumerfeignhystrix.service.impl;

import com.cz.springcloud.api.EntityApi;
import com.cz.springcloud.entity.Entity;
import com.cz.springcloudconsumerfeignhystrix.service.IEntityService;
import com.google.common.collect.ImmutableList;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 使用@ConditionalOnProperty
 * prefix application.properties配置的前缀
 * name 属性是从application.properties配置文件中读取属性值
 * havingValue 配置读取的属性值跟havingValue做比较，如果一样则返回true;否则返回false。
 * 如果返回值为false，则该configuration不生效；为true则生效
 * matchIfMissing = true表示如果没有在application.properties设置该属性，则默认为条件符合
 *
 * @author alian
 * @date 2020/10/13 下午 4:39
 * @since JDK8
 */
@ConditionalOnProperty(value = "method", havingValue = "true")
@Slf4j
@Service
public class EntityServiceImpl_FBMethod implements IEntityService {

    @Autowired
    private EntityFeignClient entityFeignClient;

    @HystrixCommand(fallbackMethod = "getAllFallback")
    @Override
    public List<Entity> getAll() {
        return this.entityFeignClient.getAll();
    }

    @HystrixCommand(fallbackMethod = "getByIdFallBack")
    @Override
    public Entity getById(Integer id) {
        return this.entityFeignClient.getById(id);
    }

    private List<Entity> getAllFallback() {
        return ImmutableList.of(new Entity(-1, "服务熔断", "from call back method", LocalDateTime.now()));
    }

    private Entity getByIdFallBack(Integer id) {
        return new Entity(id, "服务熔断", "from call back method", LocalDateTime.now());
    }
}

@ConditionalOnProperty(value = "method", havingValue = "true")
@FeignClient(name = "provider")
interface EntityFeignClient extends EntityApi {
}


