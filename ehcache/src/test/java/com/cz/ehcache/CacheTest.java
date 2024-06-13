package com.cz.ehcache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.junit.Test;

import java.io.File;
import java.time.Duration;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * @author alian
 * @date 2020/12/1 下午 5:27
 * @since JDK8
 */
public class CacheTest {

    String cacheName = "threeTieredCache";
    String filePath = "d:\\cache.file";

    @Test
    public void test() throws InterruptedException {
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .with(CacheManagerBuilder.persistence(new File(filePath, "myData")))
                .withCache(cacheName,
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,
                                ResourcePoolsBuilder.newResourcePoolsBuilder()
                                        .heap(10L, EntryUnit.ENTRIES)
                                        .offheap(1L, MemoryUnit.MB)
                                        // 第三个参数为true,代表会持久化到磁盘
                                        .disk(10L, MemoryUnit.MB, true)
                        ).withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(5L)))
                ).build(true);
        Cache<Long, String> threeTieredCache = cacheManager.getCache(cacheName, Long.class, String.class);

//        for (long i = 0; i < 11L; i++) {
//            threeTieredCache.put(i, "data put at 17:32 _ " + i);
//        }
        threeTieredCache.put(1L, "aaaa");
        System.out.println( threeTieredCache.get(1L));
        threeTieredCache.put(2L, "bbbb");
        TimeUnit.SECONDS.sleep(10);
        System.out.println(threeTieredCache.get(1L));
        System.out.println(threeTieredCache.replace(2L, "1---2"));
        cacheManager.close();
    }

    /**
     * 因为会持久化到磁盘,所以上个单元测试put的值,这个此单元测试启动另一个jvm可以get到
     */
    @Test
    public void test1() throws InterruptedException {
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .with(CacheManagerBuilder.persistence(new File(filePath, "myData")))
                .withCache(cacheName,
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,
                                ResourcePoolsBuilder.newResourcePoolsBuilder()
                                        .heap(10L, EntryUnit.ENTRIES)
                                        .offheap(1L, MemoryUnit.MB)
                                        .disk(10L, MemoryUnit.MB, true)
                        )
                ).build(true);
        Cache<Long, String> threeTieredCache = cacheManager.getCache(cacheName, Long.class, String.class);
        final String s = threeTieredCache.get(1L);
        System.out.println(s);
        cacheManager.close();
    }

    @Test
    public void test2() {
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .with(CacheManagerBuilder.persistence(new File(filePath, "myData")))
                .withCache(cacheName,
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,
                                ResourcePoolsBuilder.newResourcePoolsBuilder()
//                                        .heap(10L, EntryUnit.ENTRIES)
                                        .offheap(1L, MemoryUnit.MB)
//                                        .disk(10L, MemoryUnit.MB, true)
                        )
                ).build(true);
        Cache<Long, String> threeTieredCache = cacheManager.getCache(cacheName, Long.class, String.class);

        for (Long i = 1L; i <= 10; i++) {
            char[] b = new char[512 * 500];
            b[0] = (char) (64 + i.intValue());
            for (int i1 = 1; i1 < 512 * 500; i1++) {
                b[i1] = 'a';
            }
            System.out.println("------");
            threeTieredCache.put(i, new String(b));
            for (Long j = 1L; j <= 10; j++) {
                String value = threeTieredCache.get(j);
                if (value != null) {
                    System.out.println(j + " - > " + value.substring(0, 20));
                } else {
                    System.out.println(j + " - > " + value);
                }
            }
            System.out.println("------");
        }
        System.out.println("------");
        System.out.println("------");
        System.out.println("------");
        System.out.println("------");
        System.out.println("------");
        cacheManager.close();
        try {
            TimeUnit.SECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3() {
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .with(CacheManagerBuilder.persistence(new File(filePath, "myData")))
                .withCache(cacheName,
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,
                                ResourcePoolsBuilder.newResourcePoolsBuilder()
//                                        .heap(10L, EntryUnit.ENTRIES)
                                        .offheap(10L, MemoryUnit.MB)
//                                        .disk(10L, MemoryUnit.MB, true)
                        )
                ).build(true);
        Cache<Long, String> threeTieredCache = cacheManager.getCache(cacheName, Long.class, String.class);

        for (Long i = 0L; i < 10; i++) {
            threeTieredCache.put(i, "value_" + i);
        }
        System.out.println("------");
        for (long i = 0; i < 10L; i++) {
            System.out.println(i + " : " + threeTieredCache.get(i));
        }
        System.out.println("------");
        final Iterator<Cache.Entry<Long, String>> iterator = threeTieredCache.iterator();
        while (iterator.hasNext()) {
            Long key = iterator.next().getKey();
            if (key == 3 || key == 4) {
                threeTieredCache.remove(key);
            }
        }
        System.out.println("------");
        for (long i = 0; i < 10L; i++) {
            System.out.println(i + " : " + threeTieredCache.get(i));
        }
        cacheManager.close();
    }

}
