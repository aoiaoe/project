package com.cz.spring_boot_rabbitmq.delayqueue.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ auto configuration support.
 *
 * @author jy
 */
@Slf4j
@Configuration
@AutoConfigureAfter({JacksonAutoConfiguration.class, RabbitAutoConfiguration.class})
@ConditionalOnBean(RabbitTemplate.class)
public class RabbitMqAutoConfiguration {

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public BeanPostProcessor messageListenerContainerBeanPostProcessor() {
        return new MessageListenerContainerBeanPostProcessor();
    }


//    @Order
//    @Configuration
//    @ConditionalOnProperty("spring.rabbitmq.error.exchange")
//    public static class MessageRecoverAutoConfiguration extends ApplicationObjectSupport {
//
//        @Value("${spring.rabbitmq.error.exchange}")
//        private String errorExchange;
//
//        @Autowired
//        private RabbitTemplate rabbitTemplate;
//
//        @Bean
//        public MessageRecoverer messageRecoverer() {
//            return new RepublishMessageRecoverer(rabbitTemplate, errorExchange);
//        }
//
//        @Override
//        public void initApplicationContext() {
//            RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitTemplate);
//            // Declare error exchange.
//            rabbitAdmin.declareExchange(new DirectExchange(errorExchange));
//            // Bind error exchange with queues.
//            getApplicationContext().getBeansOfType(Binding.class)
//                .values()
//                .forEach(binding -> rabbitAdmin.declareBinding(new Binding(binding.getDestination(), Binding.DestinationType.QUEUE, errorExchange, binding.getRoutingKey(), null)));
//        }
//    }
}
