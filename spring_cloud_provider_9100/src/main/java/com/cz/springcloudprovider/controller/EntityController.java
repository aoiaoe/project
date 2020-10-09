package com.cz.springcloudprovider.controller;

import com.cz.springcloud.api.EntityApi;
import com.cz.springcloud.entity.Entity;
import com.cz.springcloudprovider.service.IEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author alian
 * @date 2020/10/9 下午 3:42
 * @since JDK8
 */
@RestController
@RequestMapping(value = "/entity")
public class EntityController implements EntityApi {

    @Autowired
    private IEntityService iEntityService;

    @GetMapping(value = "/list")
    @Override
    public List<Entity> getAll(){
        return this.iEntityService.getAll();
    }

    @GetMapping(value = "/{id}")
    @Override
    public Entity getById(@PathVariable(value = "id") Integer id) {
        return this.iEntityService.getById(id);
    }

}
