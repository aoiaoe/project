package com.cz.spring_boot_mix;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.cache.CacheResponseStatus;
import org.apache.http.client.cache.HttpCacheContext;
import org.apache.http.client.cache.HttpCacheStorage;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.cache.BasicHttpCacheStorage;
import org.apache.http.impl.client.cache.CacheConfig;
import org.apache.http.impl.client.cache.CachingHttpClients;
import org.apache.http.impl.client.cache.ImmediateSchedulingStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HttpClientTest {
    private HttpClient client;

    @Before
    public void setUp(){
        CacheConfig config = CacheConfig.custom()
                .setMaxCacheEntries(1000)// 最多缓存1000条目
                .setMaxObjectSize(1 * 1024 * 1024) // 缓存对象最大1Mb
                .setAsynchronousWorkersCore(5) // 异步额鞥新缓存线程池最小空闲线程数
                .setAsynchronousWorkersMax(10) // 异步更新缓存线程池最大线程数
                .setRevalidationQueueSize(10000) // 异步更新线程池队列大小
                .build();
        // 缓存存储
        HttpCacheStorage storage = new BasicHttpCacheStorage(config);

        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
        // 池化参数
        manager.setMaxTotal(100);

        client = CachingHttpClients.custom()
//                .setCacheConfig(config)
//                .setHttpCacheStorage(storage)
//                .setSchedulingStrategy(new ImmediateSchedulingStrategy(config))
                .setConnectionManager(manager)
                .build();
    }

    @Test
    public void test1() throws Exception {
        HttpGet get = new HttpGet("http://localhost:18104/cacheTest?agr=0");
        get.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
        HttpCacheContext cacheContext = new HttpCacheContext();
        // 第一次请求 cache_miss,
        HttpResponse execute = client.execute(get, cacheContext);
        CacheResponseStatus cacheResponseStatus = cacheContext.getCacheResponseStatus();
        System.out.println(EntityUtils.toString(execute.getEntity()));
        System.out.println(cacheResponseStatus);

        TimeUnit.SECONDS.sleep(1);
        // 第二次请求，因为已经缓存过，cache_hit, 不会请求远端服务
        System.out.println("1秒后再次请求");
//        get = new HttpGet("http://localhost:18104/cacheTest?arg=1");
//        get.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
        execute = client.execute(get, cacheContext);
        cacheResponseStatus = cacheContext.getCacheResponseStatus();
        System.out.println(EntityUtils.toString(execute.getEntity()));
        System.out.println(cacheResponseStatus);

    }

    @After
    public void tearDown(){
    }
}
