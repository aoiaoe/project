package com.cz.springbootredis.controller;

import com.cz.springbootredis.RedisCacheService;
import com.cz.springbootredis.entity.CacheObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = "/cache")
@RestController
public class RedisCacheController {

    @Autowired
    private RedisCacheService redisCacheService;

    @GetMapping(value = "/list")
    public List<CacheObj> getList(){
        return this.redisCacheService.getList();
    }

    @GetMapping(value = "/get")
    public CacheObj getCache(Long id){
        return this.redisCacheService.getCache(id);
    }

    @GetMapping(value = "/add")
    public boolean evictCache(Long id){
        return this.redisCacheService.addObj(id);
    }
}
