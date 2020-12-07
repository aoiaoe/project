package com.cz.springcloudconsumerfeignhystrixstream.service.impl;

import com.cz.springcloud.entity.Entity;
import com.cz.springcloudconsumerfeignhystrixstream.feign.EntityFeignClient;
import com.cz.springcloudconsumerfeignhystrixstream.service.EntitySerivce;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EntityServiceImpl implements EntitySerivce {

    @Autowired
    private EntityFeignClient entityFeignClient;

    @Override
    public Entity getById(Integer id) {
        return this.entityFeignClient.getById(id);
    }
}
