package com.cz.springbootredis.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;

/**
 * @author jzm
 * @date 2022/7/11 : 14:36
 */
@Component
public class RedisBrpopComponent {

    static Logger logger = LoggerFactory.getLogger(RedisBrpopComponent.class);

    @Autowired
    private RedisTemplate stringRedisTemplate;

    /**
     * 简单测试brpop
     * brpop可以提高消息时效性
     * 但是等待消息中的连接，会被判定为空闲连接
     * 如果线程一直阻塞在哪里，Redis 的客户端连接就成了闲置连接，闲置过久(spring.redis.timeout配置)，服务器一般会主动断开连接，减少闲置资源占用。
     * 这个时候blpop/brpop会抛出异常来。
     * 所以需要使用带超时的brpop方法，并且需要比spring.redis.timeout配置短
     */
//    @PostConstruct //单线程执行，会阻塞项目启动
    public void testBrpop(){
        logger.info("测试brpop");
        try {
            while (true) {
//                Object testList = stringRedisTemplate.opsForList().rightPop("testList", Duration.ofMinutes(10));
                Object testList = stringRedisTemplate.opsForList().rightPop("testList", Duration.ofSeconds(5));
                logger.info("从list中获取消息：{}", testList);
            }
        }catch (Exception e){
            logger.error("测试brpop:", e);
        }
    }

}
