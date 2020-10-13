package com.cz.springcloudconsumerfeignhystrix.service.impl;

import com.cz.springcloud.entity.Entity;
import com.cz.springcloudconsumerfeignhystrix.feign.EntityFeignClient;
import com.cz.springcloudconsumerfeignhystrix.service.IEntityService;
import com.google.common.collect.ImmutableList;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author alian
 * @date 2020/10/13 下午 4:39
 * @since JDK8
 */
@Slf4j
@Service
public class EntityServiceImpl implements IEntityService {

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

    private List<Entity> getAllFallback(){
        return ImmutableList.of(new Entity(-1, "服务熔断", ""));
    }

    private Entity getByIdFallBack(Integer id){
        return new Entity(id, "服务熔断", "");
    }
}
