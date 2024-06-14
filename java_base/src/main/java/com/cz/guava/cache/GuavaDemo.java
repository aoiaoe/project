package com.cz.guava.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class GuavaDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

//        simpleTest();

        multiThreadTest();
    }

    /*
    多线程访问只有一个线程会回源获取数据
     */
    private static void multiThreadTest() throws ExecutionException, InterruptedException{
        OriginData src = new OriginData();

        LoadingCache<String, Object> cache = CacheBuilder.newBuilder()
                // 最大三个缓存数据
                .maximumSize(3)
                // 写入三秒后过期
                .expireAfterAccess(3, TimeUnit.SECONDS)
                .recordStats()
                .removalListener(notify -> {
                    System.out.println( notify.getKey() + "被移除了, 原因:" + notify.getCause());
                })
                .build(new CacheLoader<String, Object>() {
                    @Override
                    public Object load(String s) throws Exception {
                        return src.get(s);
                    }
                });

        CyclicBarrier cb = new CyclicBarrier(10);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    cb.await();
                    log.info("data:{}",cache.get("k1"));
                    TimeUnit.SECONDS.sleep(2);
                    cb.await();
                    log.info("data:{}",cache.get("k2"));
                    TimeUnit.SECONDS.sleep(2);
                    log.info("data:{}",cache.get("k3"));
                    TimeUnit.SECONDS.sleep(5);
                    log.info("data:{}",cache.get("k1"));
                    TimeUnit.SECONDS.sleep(2);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }, "t" + i).start();
        }
        Thread.yield();
    }

    private static void simpleTest() throws ExecutionException, InterruptedException{
        OriginData src = new OriginData();

        LoadingCache<String, Object> cache = CacheBuilder.newBuilder()
                // 最大三个缓存数据
                .maximumSize(3)
                // 写入三秒后过期
                .expireAfterAccess(3, TimeUnit.SECONDS)
                .recordStats()
                .removalListener(notify -> {
                    System.out.println( notify.getKey() + "被移除了, 原因:" + notify.getCause());
                })
                .build(new CacheLoader<String, Object>() {
                    @Override
                    public Object load(String s) throws Exception {
                        return src.get(s);
                    }
                });


        System.out.println(cache.get("k1", () -> {
            Object k1 = src.get("k1");
            return k1;
        }));
        System.out.println(cache.get("k2"));
        System.out.println(cache.get("k3"));
        System.out.println(cache.get("k4"));
        System.out.println(cache.get("k1"));

        TimeUnit.SECONDS.sleep(4);
        System.out.println(cache.get("k1"));
        System.out.println(cache.getIfPresent("k5"));
        System.out.println(cache.stats().toString());
    }
}
