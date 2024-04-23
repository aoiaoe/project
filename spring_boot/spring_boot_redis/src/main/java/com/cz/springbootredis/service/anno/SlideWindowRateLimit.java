package com.cz.springbootredis.service.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 简单滑动窗口限流器
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SlideWindowRateLimit {

    /**
     * 阈值
     * @return
     */
    public int count();

    /**
     * 窗口大小  秒钟
     * @return
     */
    public int windowSecond() default 60;
}
