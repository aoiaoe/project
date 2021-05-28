package com.cz.springcloudconsumerfeignhystrix.service.impl;

import com.cz.springcloud.api.EntityApi;
import com.cz.springcloud.entity.Entity;
import com.cz.springcloudconsumerfeignhystrix.service.IEntityService;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 通过Feign客户端的fallback属性开启回退方法
 * 需要要开启以下配置:
 * feign:
 *   hystrix:
 *     enabled: true
 * @author alian
 * @date 2020/10/13 下午 4:39
 * @since JDK8
 */
@ConditionalOnProperty(value = "prop",  havingValue = "true")
@Slf4j
@Service
public class EntityServiceImpl_FBProp implements IEntityService {

    @Autowired
    private EntityFeignFBPropClient entityFeignFBPropClient;

    @Override
    public List<Entity> getAll() {
        return this.entityFeignFBPropClient.getAll();
    }

    @Override
    public Entity getById(Integer id) {
        return this.entityFeignFBPropClient.getById(id);
    }
}

@ConditionalOnProperty(value = "prop",  havingValue = "true")
@FeignClient(name = "provider", path = "/entity", fallback = EntityFeignFallBack.class)
interface EntityFeignFBPropClient{

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    List<Entity> getAll();

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    Entity getById(@PathVariable(value = "id") Integer id);
}

@ConditionalOnProperty(value = "prop",  havingValue = "true")
@Component
class EntityFeignFallBack implements EntityFeignFBPropClient{

    @Override
    public List<Entity> getAll() {
        return ImmutableList.of(new Entity(-1, "服务熔断", "from @FeignClient callback prop", LocalDateTime.now()));
    }

    @Override
    public Entity getById(Integer id) {
        return new Entity(-2, "服务熔断", "from @FeignClient callback prop", LocalDateTime.now());
    }
}

