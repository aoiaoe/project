package com.cz.service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@Service
public class OrderService {

    // redis中lua脚本的sha,先将脚本预加载到redis服务器中，可以减少网络传输和脚本编译时间
    private final static String SCRIPT_SHA = "23257fcceb115c68f798b41fa155c920094a6bad";
    // 真实的脚本如下
    private final static String SCRIPT = "local c_s = redis.call('get', KEYS[1])\n" +
            "if not c_s or tonumber(c_s) < tonumber(KEYS[2]) then\n" +
            "    return 0\n" +
            "end\n" +
            "local c_a = redis.call('decrby',KEYS[1], KEYS[2])\n" +
            "return 1";
    private final static String SEC_KILL_KEY_PREFIX = "sec_kill:";

    @Autowired
    private RedisTemplate redisTemplate;

    public boolean createOrder(String productId) {
        // 限购
        Object res = redisTemplate.execute(new RedisScript() {
            @Override
            public String getSha1() {
                return SCRIPT_SHA;
            }

            @Override
            public Class getResultType() {
                return Long.class;
            }

            @Override
            public String getScriptAsString() {
                return null;
            }
        }, Arrays.asList(SEC_KILL_KEY_PREFIX + productId, "1"));
        Long count = (Long)res;
        log.info("是否抢购到商品：{}", count);
        if(count == null || count <= 0){
            return false;
        }
        return true;
    }
}
