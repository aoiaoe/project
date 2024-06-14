package com.cz.springbootredis.service.anno;

import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.UUID;

/**
 * 滑动窗口限流器
 */
@Aspect
@Component
public class SlideWindowRateLimitAspect {

    private static final String SLIDE_WINDOW_RATE_LIMIT_LUA_SCRIPT = "" +
            // 计算滑动窗口起始时间
            "local window_start = tonumber(ARGV[1]) - tonumber(ARGV[4]) " +
            // 移除滑动窗口以外的数据
            "redis.call('ZREMRANGEBYSCORE', KEYS[1], '-inf', window_start) " +
            // 统计窗口中还有几个数据
            "local count = redis.call('ZCARD', KEYS[1]) " +
            // 判断是否超过阈值
            "if count < tonumber(ARGV[3]) then " +
            "  redis.call('ZADD', KEYS[1], tonumber(ARGV[1]), ARGV[2]) " +
            "  return 1 " +
            "else " +
            "   return 0 " +
            "end";
    private static final RedisScript<Long> SCRIPT = new DefaultRedisScript(SLIDE_WINDOW_RATE_LIMIT_LUA_SCRIPT, Long.class);
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Pointcut(value = "@annotation(com.cz.springbootredis.service.anno.SlideWindowRateLimit)")
    public void pointCut(){}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint point){
        try {
            MethodSignature signature = (MethodSignature) point.getSignature();
            Method method = signature.getMethod();
            SlideWindowRateLimit annotation = method.getAnnotation(SlideWindowRateLimit.class);
            // 限流个数
            int count = annotation.count();
            int windowSeconds = annotation.windowSecond();
            long now = System.currentTimeMillis();
            String value = createValue();
            ServletRequestAttributes servletWebRequest = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            // nginx转发
            String ip = servletWebRequest.getRequest().getHeader("remote_addr");
            String key = signature.getName() + ":" + ip;
            Long res = this.redisTemplate.execute(SCRIPT, Arrays.asList(key), now, value, count, windowSeconds * 1000);
            if (res != 1) {
                throw new RuntimeException("访问过于频繁，请稍候再试");
            }
            return point.proceed();
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }catch (Throwable e){
            e.printStackTrace();
            throw new RuntimeException("服务器限流异常，请稍候再试");
        }
    }

    private String createValue() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
