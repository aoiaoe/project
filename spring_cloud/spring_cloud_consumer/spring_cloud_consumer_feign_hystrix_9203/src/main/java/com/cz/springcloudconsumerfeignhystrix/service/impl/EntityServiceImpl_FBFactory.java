package com.cz.springcloudconsumerfeignhystrix.service.impl;

import com.cz.springcloud.api.EntityApi;
import com.cz.springcloud.entity.Entity;
import com.cz.springcloudconsumerfeignhystrix.service.IEntityService;
import com.google.common.collect.ImmutableList;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author alian
 * @date 2020/10/13 下午 4:39
 * @since JDK8
 */
@ConditionalOnProperty(value = "factory", havingValue = "true")
@Slf4j
@Service
public class EntityServiceImpl_FBFactory implements IEntityService {

    @Autowired
    private EntityFeignFBFactoryClient entityFeignFBFactoryClient;

    @Override
    public List<Entity> getAll() {
        return this.entityFeignFBFactoryClient.getAll();
    }

    @Override
    public Entity getById(Integer id) {
        return this.entityFeignFBFactoryClient.getById(id);
    }
}

@ConditionalOnProperty(value = "factory", havingValue = "true")
@FeignClient(name = "provider", fallbackFactory = EntityFallBackFactory.class)
interface EntityFeignFBFactoryClient extends EntityApi {
}


@Slf4j
@ConditionalOnProperty(value = "factory", havingValue = "true")
@Component
class EntityFallBackFactory implements FallbackFactory<EntityFeignFBFactoryClient> {
    @Override
    public EntityFeignFBFactoryClient create(Throwable throwable) {
        log.error("回退错误:{}", throwable);
        return new EntityFeignFBFactoryClient() {

            @Override
            public List<Entity> getAll() {
                return ImmutableList.of(new Entity(-1, "服务熔断", "from call back factory", LocalDateTime.now()));
            }

            @Override
            public Entity getById(Integer id) {
                return new Entity(-2, "服务熔断", "from call back factory", LocalDateTime.now());
            }
        };
    }
}