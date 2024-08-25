package com.cz.springbootredis.service;

import com.cz.springbootredis.entity.CacheObj;
import com.cz.springbootredis.entity.CacheObjHolder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@ConditionalOnProperty(value = "cache.type", havingValue = "springCache")
public class RedisCacheServiceImpl implements CacheService{

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

    // 批量失效
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "sbrediscache", key = "'cachobjs'"),
                    @CacheEvict(cacheNames = "sbrediscache", key = "#id"),
            }
    )
    @Override
    public CacheObj updateObj(Long id) {
        System.out.println("addObj 修改对象");
        CacheObj obj = CacheObjHolder.holders.get(id);
        if (obj == null) {
            throw new RuntimeException("数据不存在");
        }
        Random random = new Random();
        obj = new CacheObj(id, "name" + id, random.nextInt(50) + 10);
        CacheObjHolder.holders.put(id, obj);
        return obj;
    }

    @CacheEvict(cacheNames = "sbrediscache", key = "#id")
    @Override
    public boolean deleteObj(Long id) {
        CacheObjHolder.holders.remove(id);
        return true;
    }
}
