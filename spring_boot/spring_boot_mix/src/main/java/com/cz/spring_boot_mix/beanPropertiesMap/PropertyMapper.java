package com.cz.spring_boot_mix.beanPropertiesMap;

import com.alibaba.fastjson.JSONObject;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.*;
import java.util.function.Function;

/**
 * @author jy
 */
public final class PropertyMapper {

    private static final ExpressionParser PARSER = new SpelExpressionParser(new SpelParserConfiguration(true, true));

    private final StandardEvaluationContext context;

    private final Map<String, Object> targetObject = new HashMap<>();

    public PropertyMapper(Object originalObject) {
        this.context = new StandardEvaluationContext(Optional.ofNullable(originalObject).orElse(Collections.emptyMap()));
        this.context.addPropertyAccessor(new JSONObjectPropertyAccessor());
    }

    public <T> PropertyMapper mapProperty(String expression, Class<T> type, String targetProperty, Function<Object, Object> valueFunction) {
        T value = PARSER.parseExpression(expression).getValue(context, type);
        if (valueFunction != null) {
            set(targetProperty, valueFunction.apply(value));
        } else {
            set(targetProperty, value);
        }
        return this;
    }

    public PropertyMapper mapProperty(String expression, String targetProperty) {
        return mapProperty(expression, null, targetProperty);
    }

    public <T> PropertyMapper mapProperty(String expression, Class<T> type, String targetProperty) {
        return mapProperty(expression, type, targetProperty, null);
    }

    public PropertyMapper mapProperty(List<MappedPropertyConfig> configs) {
        configs.forEach(config -> mapProperty(config.getExpression(), config.getType(), config.getTargetProperty(), config.getValueFunction()));
        return this;
    }

    public void set(String property, Object value) {
        if (value != null) {
            targetObject.put(property, value);
        }
    }

    public JSONObject getTargetObject() {
        return new JSONObject(targetObject);
    }

    public <T> T unwrap(Class<T> type) {
        return getTargetObject().toJavaObject(type);
    }
}
