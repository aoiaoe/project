package com.cz.spring_cloud_alibaba.anntation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识注解，用于在生成统一响应式，进行忽略
 * 标注了此注解的不会生成统一响应包装过的内容
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface IgnoreResponseBody {
}
