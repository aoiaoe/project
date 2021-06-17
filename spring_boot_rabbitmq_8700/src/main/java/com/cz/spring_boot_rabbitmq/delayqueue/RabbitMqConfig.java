package com.cz.spring_boot_rabbitmq.delayqueue;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMqConfig {

    public static final String DELAY_TOPIC_EXCHANGE = "delay.topic.exchange";
    public static final String DELAY_QUEUE = "delay.queue";
    public static final String PROCESS_TOPIC_EXCHANGE = "process.topic.exchange";
    public static final String PROCESS_QUEUE = "process.queue";
    public static final String KEY = "#";

    // 针对每条消息设置过期时间，过期没消费 则会发送到死信队列
    public static int DELAY_MILLS = 10_000;

    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory rabbitConnectionFactory) {
        return new RabbitTemplate(rabbitConnectionFactory);
    }

    @Bean(value = "containerFactory")
    public RabbitListenerContainerFactory<?> containerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        // 设置确认模式手工确认
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        factory.setConcurrentConsumers(2);
        factory.setPrefetchCount(1);
        return factory;
    }


    @Bean
    public TopicExchange delayExchange() {
        return new TopicExchange(DELAY_TOPIC_EXCHANGE, false, true);
    }

    @Bean
    public Queue delayQueue() {
        Map<String, Object> argsMap = new HashMap();
        argsMap.put("x-dead-letter-exchange", PROCESS_TOPIC_EXCHANGE);
        argsMap.put("x-dead-letter-routing-key", KEY);
        return new Queue(DELAY_QUEUE, false, false, true, argsMap);
    }

    @Bean
    public Binding delayBinding() {
        return BindingBuilder.bind(delayQueue()).to(delayExchange()).with(KEY);
    }

    @Bean
    public TopicExchange processExchange() {
        return new TopicExchange(PROCESS_TOPIC_EXCHANGE, false, true);
    }

    @Bean
    public Queue processQueue() {
        return new Queue(PROCESS_QUEUE, false, false, true);
    }

    @Bean
    public Binding processBinding() {
        return BindingBuilder.bind(processQueue()).to(processExchange()).with(KEY);
    }


}
