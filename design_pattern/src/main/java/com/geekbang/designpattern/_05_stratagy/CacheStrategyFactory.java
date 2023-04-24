package com.geekbang.designpattern._05_stratagy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jzm
 * @date 2023/4/24 : 10:03
 */
public class CacheStrategyFactory {

    private static Map<StrategyEnum, ICacheStrategy> strategyMap = new ConcurrentHashMap<>();

    static {
        strategyMap.put(StrategyEnum.Redis, new RedisCacheStrategy());
        strategyMap.put(StrategyEnum.Ehcache, new EhCacheStrategy());
    }

    public static ICacheStrategy matchStrategy(StrategyEnum type){
        return strategyMap.get(type);
    }

}
