package com.cz;

import com.cz.springbootredis.SpringBootRedisApplication;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StopWatch;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = SpringBootRedisApplication.class)
public class RedisTest {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    private static final String COUPONS = "user:coupon:";
    private static final String COUPONS_PIPELINE = "user:coupon_pipeline:";
    private static final String ID = "a";
    private static final String FROM = "b";
    private static final String TO = "c";
    private static final String AMOUNT = "d";

    @Test
    public void testMultiGet() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < 5; i++) {
            List<Object> objects = this.redisTemplate.opsForHash().multiGet(COUPONS + i, Arrays.asList(new String[]{ID, FROM, TO, AMOUNT}));
            objects.forEach(e -> System.out.println(e));
            System.out.println("---");
        }
        stopWatch.stop();
        System.out.println(stopWatch.getLastTaskInfo().getTimeMillis());
    }

    @Test
    public void testHgetPipeline() {
        StopWatch stopWatch = new StopWatch();
        List<Map> list = new ArrayList<>();
        stopWatch.start();
        this.redisTemplate.executePipelined(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                for (int i = 0; i < 5; i++) {
                    byte[] key = (COUPONS_PIPELINE + i).getBytes(StandardCharsets.UTF_8);
                    Map<byte[], byte[]> map = connection.hGetAll(key);
                    System.out.println(map);
                    list.add(map);
                }
                return null;
            }
        });
        list.forEach(e -> System.out.println(e));
        stopWatch.stop();
        System.out.println(stopWatch.getLastTaskInfo().getTimeMillis());
    }

    @Test
    public void testSave() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < 5; i++) {
            this.redisTemplate.opsForHash().put(COUPONS + i, ID, i);
            this.redisTemplate.opsForHash().put(COUPONS + i, FROM, System.currentTimeMillis());
            this.redisTemplate.opsForHash().put(COUPONS + i, TO, System.currentTimeMillis());
            this.redisTemplate.opsForHash().put(COUPONS + i, AMOUNT, 10);
        }
        stopWatch.stop();
        System.out.println(stopWatch.getLastTaskInfo().getTimeMillis());
    }

    @Test
    public void testSavePipeline() {
        StopWatch stopWatch = new StopWatch();
        byte[] id = ID.getBytes(StandardCharsets.UTF_8);
        byte[] fr = FROM.getBytes(StandardCharsets.UTF_8);
        byte[] to = TO.getBytes(StandardCharsets.UTF_8);
        byte[] amount = AMOUNT.getBytes(StandardCharsets.UTF_8);
        byte[] ten = ("10").getBytes(StandardCharsets.UTF_8);
        stopWatch.start();
        this.redisTemplate.executePipelined(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                for (int i = 0; i < 5; i++) {
                    byte[] key = (COUPONS_PIPELINE + i).getBytes(StandardCharsets.UTF_8);
                    connection.hSet(key, id, (i + "").getBytes(StandardCharsets.UTF_8));
                    connection.hSet(key, fr, (System.currentTimeMillis() + "").getBytes(StandardCharsets.UTF_8));
                    connection.hSet(key, to, (System.currentTimeMillis() + "").getBytes(StandardCharsets.UTF_8));
                    connection.hSet(key, amount, ten);
                }
                return null;
            }
        });

        stopWatch.stop();
        System.out.println(stopWatch.getLastTaskInfo().getTimeMillis());
    }
}
