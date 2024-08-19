package com.cz.springbootredis.service;

import com.alicp.jetcache.anno.*;
import com.cz.springbootredis.entity.CacheObj;
import com.cz.springbootredis.entity.CacheObjHolder;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * jetcache提供统一的API和注解来简化缓存的使用, 简化二级缓存操作的框架
 */
@ConditionalOnProperty(value = "cache.type", havingValue = "jetcache")
@Service
public class JetCacheServiceImpl implements CacheService {

    /**
     * 需要注意的地方:
     *      cacheType: 默认是REMOTE
     *      expire: 单位默认是秒, 可以用timeUnit设置单位
     *      keyConvertor = KeyConvertor.NONE: 生成出来的有点奇葩值
     * @return
     */
    @Cached(cacheType = CacheType.BOTH, name = "cacheObj:all", keyConvertor = KeyConvertor.NONE, expire = 20, localExpire = 20000)
    @Override
    public List<CacheObj> getList() {
        System.out.println("getList 获取所有对象");
        return new ArrayList<>(CacheObjHolder.holders.values());
    }

    @Cached(area = "default", name = "cacheObj:", key = "#id", expire = 40000, localExpire = 20)
    @Override
    public CacheObj getCache(Long id) {
        System.out.println("getCache 获取指定Id对象");
        return CacheObjHolder.holders.get(id);
    }

    @CacheInvalidate(name = "cacheObj:all")
    @Override
    public boolean addObj(Long id) {
        System.out.println("addObj 增加对象");
        Random random = new Random();
        CacheObjHolder.holders.put(id, new CacheObj(id, "name" + id, random.nextInt(50) + 10));
        return true;
    }

    // TODO 更新还有问题
    @CacheUpdate(name = "cacheObj:", key = "#id", value = "#obj")
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

    // TODO 分布式系统过期本地缓存
}
