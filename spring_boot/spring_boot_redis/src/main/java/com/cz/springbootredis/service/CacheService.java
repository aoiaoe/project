package com.cz.springbootredis.service;

import com.cz.springbootredis.entity.CacheObj;
import com.cz.springbootredis.entity.CacheObjHolder;

import java.util.List;
import java.util.Random;

public interface CacheService {

    List<CacheObj> getList();

    CacheObj getCache(Long id);

    boolean addObj(Long id);

    CacheObj updateObj(Long id);

    boolean deleteObj(Long id);

    default CacheObj updateNoCache(Long id) {
        System.out.println("addObj 修改对象 不操作缓存");
        CacheObj obj = CacheObjHolder.holders.get(id);
        if (obj == null) {
            throw new RuntimeException("数据不存在");
        }
        Random random = new Random();
        obj = new CacheObj(id, "No cache Updated: name" + id, random.nextInt(50) + 10);
        CacheObjHolder.holders.put(id, obj);
        return obj;
    }
}
