package com.cz.springcloudconsumerfeignhystrix.feign;

import com.cz.springcloud.entity.Entity;
import com.google.common.collect.ImmutableList;
import feign.hystrix.FallbackFactory;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author alian
 * @date 2020/10/13 下午 4:48
 * @since JDK8
 */
@Configuration
public class EntityFeignFallbackFactory implements FallbackFactory<EntityFeignClient> {
    @Override
    public EntityFeignClient create(Throwable throwable) {
        return new EntityFeignClient() {
            @Override
            public List<Entity> getAll() {
                return ImmutableList.of(new Entity(-1, "服务熔断", "fall back factory"));
            }

            @Override
            public Entity getById(Integer id) {
                return new Entity(id, "服务熔断", "fall back factory");
            }
        };
    }
}
