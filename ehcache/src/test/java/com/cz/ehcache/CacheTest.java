package com.cz.ehcache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
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
    public void test(){
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .with(CacheManagerBuilder.persistence(new File(filePath, "myData")))
                .withCache(cacheName,
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,
                                ResourcePoolsBuilder.newResourcePoolsBuilder()
                                        .heap(10L, EntryUnit.ENTRIES)
                                        .offheap(1L, MemoryUnit.MB)
                                        // 第三个参数为true,代表会持久化到磁盘
                                        .disk(10L, MemoryUnit.MB, true)
                        )
                ).build(true);
        Cache<Long, String> threeTieredCache = cacheManager.getCache(cacheName, Long.class, String.class);

//        for (long i = 0; i < 11L; i++) {
//            threeTieredCache.put(i, "data put at 17:32 _ " + i);
//        }
        threeTieredCache.put(1L, "data put at 17:32 _ ");
        System.out.println(threeTieredCache.get(1L));
        threeTieredCache.put(1L, "data put at 12:16 _ ");
        System.out.println(threeTieredCache.get(1L));
        System.out.println(threeTieredCache.replace(2L, "1---2"));
        cacheManager.close();
    }

    /**
     * 因为会持久化到磁盘,所以上个单元测试put的值,这个此单元测试启动另一个jvm可以get到
     */
    @Test
    public void test1(){
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
    public void test2(){
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .with(CacheManagerBuilder.persistence(new File(filePath, "myData")))
                .withCache(cacheName,
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, Byte[].class,
                                ResourcePoolsBuilder.newResourcePoolsBuilder()
//                                        .heap(10L, EntryUnit.ENTRIES)
                                        .offheap(2L, MemoryUnit.GB)
//                                        .disk(10L, MemoryUnit.MB, true)
                        )
                ).build(true);
        Cache<Long, Byte[]> threeTieredCache = cacheManager.getCache(cacheName, Long.class, Byte[].class);
        for (Long i = 1L; i < 50; i++) {
            Byte[] b = new Byte[1024*1024* 20];
            b[0] = 1;
            b[1] = 2;
            b[2] = 3;
            threeTieredCache.put(i, b);
        }

        Byte[] byteArr = threeTieredCache.get(2L);
        System.out.println(Arrays.toString(byteArr));
        cacheManager.close();
        try {
            TimeUnit.SECONDS.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
    }

}
