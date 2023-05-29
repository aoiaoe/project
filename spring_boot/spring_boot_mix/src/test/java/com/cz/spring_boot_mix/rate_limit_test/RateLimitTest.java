package com.cz.spring_boot_mix.rate_limit_test;

import com.geekbang.in_action.rate_limit.v1.limiter.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @author jzm
 * @date 2023/4/27 : 14:47
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RateLimitTest {

    @Test
    public void test(){
        RateLimiter rateLimiter = new RateLimiter();
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                while (true){
                    try {
                        boolean rate = rateLimiter.limit("app1", "/v1/user");
                        log.info("是否获取执行权限:{}", rate);
                        try {
                            TimeUnit.SECONDS.sleep(3);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        try {
            TimeUnit.SECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
