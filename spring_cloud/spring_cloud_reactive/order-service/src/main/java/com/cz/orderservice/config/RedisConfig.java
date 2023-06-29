package com.cz.orderservice.config;

import com.cz.orderservice.domain.Order;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    ReactiveRedisTemplate<String, String> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        return new ReactiveRedisTemplate<>(factory, RedisSerializationContext.string());
    }

    @Bean
    ReactiveRedisTemplate<String, Order> redisOperations(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<Order> serializer = new Jackson2JsonRedisSerializer<>(Order.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, Order> builder = RedisSerializationContext
                .newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, Order> context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }

}
