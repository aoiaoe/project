package com.cz.springcloudconsumerfeignhystrix.controller;

import com.cz.springcloud.entity.Entity;
import com.cz.springcloudconsumerfeignhystrix.service.IEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private IEntityService iEntityService;

    @GetMapping(value = "/{id}")
    public Entity getById(@PathVariable("id") Integer id){
        return this.iEntityService.getById(id);
    }

    @GetMapping
    public List<Entity> listAll(){
        return this.iEntityService.getAll();
    }

}
