package com.geekbang.designpattern._05_stratagy;

/**
 * @author jzm
 * @date 2023/4/24 : 10:08
 */
public class StrategyDemo {

    public static void main(String[] args) {
        StrategyEnum type = StrategyEnum.Ehcache;
        if(type == StrategyEnum.Ehcache){
            new EhCacheStrategy().save(null, null);
        } else if(type == StrategyEnum.Redis){
            new RedisCacheStrategy().save(null, null);
        }
        // 上面的一串if else 可以使用下面的策略模式+工厂模式替代
        CacheStrategyFactory.matchStrategy(type).save(null, null);
    }
}
