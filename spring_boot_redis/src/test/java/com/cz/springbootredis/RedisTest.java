package com.cz.springbootredis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author alian
 * @date 2021/2/4 上午 10:42
 * @since JDK8
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootRedisApplication.class)
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test1(){
        String key = "testSet";
        BoundSetOperations<String, String> setOperations = stringRedisTemplate.boundSetOps(key);
        setOperations.add("v1", "v2", "v3");

        setOperations.members().forEach(System.out::println);

        System.out.println(setOperations.isMember("v2"));
        System.out.println(setOperations.isMember("v4"));



//        stringRedisTemplate.opsForSet().
    }

    @Test
    public void test2(){

    }

}
