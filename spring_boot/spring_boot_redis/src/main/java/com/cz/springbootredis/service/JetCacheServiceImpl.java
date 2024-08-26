package com.cz.springbootredis.service;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.CacheManager;
import com.alicp.jetcache.RefreshPolicy;
import com.alicp.jetcache.anno.*;
import com.alicp.jetcache.template.QuickConfig;
import com.cz.springbootredis.entity.CacheObj;
import com.cz.springbootredis.entity.CacheObjHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * jetcache提供统一的API和注解来简化缓存的使用, 简化二级缓存操作的框架
 */
@Slf4j
@Service
@SuppressWarnings("all")
@ConditionalOnProperty(value = "cache.type", havingValue = "jetcache")
public class JetCacheServiceImpl implements CacheService {

    private static final String CACHE_PREFIX = ":cacheObj:";
    private static final String CACHE_ALL = ":cacheObj:all";
    @Autowired
    private CacheManager cacheManager;

    private Cache<Long, CacheObj> cache;
    private ScheduledExecutorService scheduledExecutorService;

    @PostConstruct
    public void init() {
        // 一个值得注意的地方是，jetcache是以name去寻找缓存实例，所以如果这里的配置先生效，生成了缓存实例
        // 那么下面的@Cache配置的同name的配置会以当前的配置上校，注解上面的配置就无效了，最明显的就是失效时间
        QuickConfig qc = QuickConfig.newBuilder(CACHE_PREFIX)
                .localExpire(Duration.ofSeconds(1000))
                .expire(Duration.ofSeconds(1000))
                .cacheNullValue(true)
                .cacheType(CacheType.BOTH)
                .syncLocal(true)
                .refreshPolicy(RefreshPolicy.newPolicy(10, TimeUnit.SECONDS))
                .build();
        // 如果只是简单使用缓存，可以直接使用注解声明式实现
        // 一些特殊的需求，比如说延迟双删，则需要编程式方式实现
        cache = cacheManager.getOrCreateCache(qc);
        CacheObj obj = CacheObjHolder.holders.get(1L);
        cache.put(1L, obj);
        log.info("编程式二级缓存执行成功:{}", obj);
        scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
    }

    /**
     * 需要注意的地方:
     *      cacheType: 默认是REMOTE
     *      expire: 单位默认是秒, 可以用timeUnit设置单位
     *      keyConvertor = KeyConvertor.NONE: 生成出来的有点奇葩值
     */
    @EnableCache
    @Cached(cacheType = CacheType.BOTH, name = CACHE_ALL, key = "''", keyConvertor = KeyConvertor.NONE, expire = 20, localExpire = 20000)
    @Override
    public List<CacheObj> getList() {
        log.info("getList 获取所有对象");
        return new ArrayList<>(CacheObjHolder.holders.values());
    }

    /**
     * 注解@CacheRefresh 用于刷新缓存, 需要注意的是,如果开启了编程式注解，配置生成在此注解解析之前，那么此处的刷新注解不会生效
     * 比如此处虽然配置了缓存刷新，但是在 init()方法中，已经通过注入的manager生成了同名的缓存，并且没有设置刷新，所以此方法
     * 会使用init()方法中生成的cache配置，并不会刷新缓存
     */
    @Cached(cacheType = CacheType.BOTH, name = CACHE_PREFIX, key = "#id", expire = 40000, localExpire = 20000)
    @CacheRefresh(refresh = 10, timeUnit = TimeUnit.SECONDS)
    @Override
    public CacheObj getCache(Long id) {
        log.info("getCache 获取指定Id对象:{}", id);
        return CacheObjHolder.holders.get(id);
    }

    @CacheInvalidate(name = "cacheObj:all", key = "''")
    @Override
    public boolean addObj(Long id) {
        log.info("addObj 增加对象:{}", id);
        Random random = new Random();
        CacheObjHolder.holders.put(id, new CacheObj(id, "name" + id, random.nextInt(50) + 10));
        return true;
    }

    /**
     * 如果需要使用返回值更新缓存，则必须使用 #result表达式获取返回值
     * multi代表是否key value是多个，比如说List，  不是指多级缓存
     */
    @CacheUpdate(name = CACHE_PREFIX, key = "#id", value = "#result", multi = false)
    @Override
    public CacheObj updateObj(Long id) {
        log.info("updateObj 修改对象:{}", id);
        CacheObj obj = CacheObjHolder.holders.get(id);
        if (obj == null) {
            throw new RuntimeException("数据不存在");
        }
        Random random = new Random();
        obj = new CacheObj(id, "Updated: name" + id, random.nextInt(50) + 10);
        CacheObjHolder.holders.put(id, obj);

        // 10秒后  延迟双删
//        log.info("触发延迟双删任务");
//        scheduledExecutorService.schedule(() -> {
//            cache.remove(id);
//            log.info("延迟双删成功!!!");
//        }, 10, TimeUnit.SECONDS);
        return obj;
    }

    @CacheInvalidate(name = CACHE_PREFIX, key = "#id")
    @Override
    public boolean deleteObj(Long id) {
        log.info("deleteObj 删除缓存:{}", id);
        CacheObjHolder.holders.remove(id);
        return true;
    }
}
