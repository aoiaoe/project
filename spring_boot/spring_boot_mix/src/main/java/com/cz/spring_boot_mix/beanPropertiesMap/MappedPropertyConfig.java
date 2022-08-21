package com.cz.spring_boot_mix.beanPropertiesMap;

import lombok.Data;

import java.util.function.Function;

/**
 * @author jy
 */
@Data
public class MappedPropertyConfig {

    /**
     * 返回字段
     */
    private String expression;

    private Class<?> type;

    /**
     * 目标字段（平台字段）
     */
    private String targetProperty;

    private Function<Object, Object> valueFunction;

    public MappedPropertyConfig(String expression, String targetProperty) {
        this.expression = expression;
        this.targetProperty = targetProperty;
    }

    public MappedPropertyConfig(String expression, Class<?> type, String targetProperty) {
        this.expression = expression;
        this.type = type;
        this.targetProperty = targetProperty;
    }

    public MappedPropertyConfig(String expression, String targetProperty, Function<Object, Object> func) {
        this.expression = expression;
        this.targetProperty = targetProperty;
        this.valueFunction = func;
    }
}
