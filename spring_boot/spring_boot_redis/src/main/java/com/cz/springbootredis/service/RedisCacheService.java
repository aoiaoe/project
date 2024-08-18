package com.cz.springbootredis.service;

import com.cz.springbootredis.entity.CacheObj;
import com.cz.springbootredis.entity.CacheObjHolder;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class RedisCacheService {

    @Cacheable(cacheNames = "sbrediscache", key = "'cachobjs'")
    public List<CacheObj> getList() {
        System.out.println("getList 获取所有对象");
        return new ArrayList<>(CacheObjHolder.holders.values());
    }

    @Cacheable(cacheNames = "sbrediscache", key = "#id")
    public CacheObj getCache(Long id) {
        System.out.println("getCache 获取指定Id对象");
        return CacheObjHolder.holders.get(id);
    }

    // 批量失效
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "sbrediscache", key = "'cachobjs'"),
                    @CacheEvict(cacheNames = "sbrediscache", key = "#id"),
            }
    )
    public boolean addObj(Long id) {
        System.out.println("addObj 增加对象");
        Random random = new Random();
        CacheObjHolder.holders.put(id, new CacheObj(id, "name" + id, random.nextInt(50) + 10));
        return true;
    }
}
