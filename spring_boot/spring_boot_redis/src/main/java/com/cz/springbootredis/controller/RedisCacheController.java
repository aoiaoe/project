package com.cz.springbootredis.controller;

import com.cz.springbootredis.service.CacheService;
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
    private CacheService cacheService;

    @GetMapping(value = "/list")
    public List<CacheObj> getList(){
        return this.cacheService.getList();
    }

    @GetMapping(value = "/get")
    public CacheObj getCache(Long id){
        return this.cacheService.getCache(id);
    }

    @GetMapping(value = "/add")
    public boolean addSoEvictCache(Long id){
        return this.cacheService.addObj(id);
    }

    @GetMapping(value = "/update")
    public CacheObj update(Long id){
        return this.cacheService.updateObj(id);
    }

    @GetMapping(value = "/updateNoCache")
    public CacheObj updateNoCache(Long id){
        return this.cacheService.updateNoCache(id);
    }

    @GetMapping(value = "/delete")
    public boolean delete(Long id){
        return this.cacheService.deleteObj(id);
    }
}
