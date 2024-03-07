package com.cz.spring_boot_redisson;

import com.cz.spring_boot_redisson.codec.FastjsonCodec;
import com.cz.spring_boot_redisson.config.MyRedissonConfig;
import com.cz.spring_boot_redisson.entity.UserDTO;
import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @author jzm
 * @date 2023/5/18 : 14:32
 */
public class RedissonTest {

    @Test
    public void testRBucket() {
        RedissonClient client = getClient();
// RList 继承了 java.util.List 接口
        RBucket<String> rstring =
                client.getBucket("redission:test:bucket:string");
        rstring.set("this is a string");
        RBucket<UserDTO> ruser =
                client.getBucket("redission:test:bucket:user");
        UserDTO dto = new UserDTO();
        dto.setName("jzm");
        dto.setSex("male");
        dto.setAge(222);
        dto.setToken(UUID.randomUUID().toString());
        ruser.set(dto);
        System.out.println("string is: " + rstring.get());
        System.out.println("dto is: " + ruser.get());
        client.shutdown();
    }

    @Test
    public void testTryLock(){
        RedissonClient client = getClient();
        RLock lock = client.getLock("myLock");
        try {
            if(lock.tryLock()){
                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            if(lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
    }

    public RedissonClient getClient(){
        Config config = new Config();
        config.useSingleServer()
                .setAddress(MyRedissonConfig.ADDR);
        // 设置序列化方式
//        config.setCodec(new FastjsonCodec());
        // 默认连接上 127.0.0.1:6379
        return Redisson.create(config);
    }
}
