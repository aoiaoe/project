package com.cz.springcloudconsumerfeign.controller;

import com.cz.springcloud.entity.Entity;
import com.cz.springcloudconsumerfeign.feign.EntityFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author alian
 * @date 2020/10/12 下午 6:06
 * @since JDK8
 */
@RequestMapping(value = "/entityConsumer")
@RestController
public class EntityNormalHttpController {

    @Autowired
    private EntityFeignClient entityFeignClient;

    @GetMapping(value = "/{id}")
    public Entity getById(@PathVariable("id") Integer id) {
        return this.entityFeignClient.getById(id);
    }

    @GetMapping
    public List<Entity> listAll() {
        return this.entityFeignClient.getAll();
    }

}
