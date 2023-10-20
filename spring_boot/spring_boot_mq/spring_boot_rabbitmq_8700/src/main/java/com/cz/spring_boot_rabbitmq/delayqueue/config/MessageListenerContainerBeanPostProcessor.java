package com.cz.spring_boot_rabbitmq.delayqueue.config;

import org.springframework.amqp.rabbit.config.AbstractRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author jy
 */
public class MessageListenerContainerBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof AbstractRabbitListenerContainerFactory) {
            AbstractRabbitListenerContainerFactory containerFactory = (AbstractRabbitListenerContainerFactory) bean;
            containerFactory.setAfterReceivePostProcessors(new LogMessagePostProcessor());
        }
        if (bean instanceof RabbitTemplate) {
            RabbitTemplate rabbitTemplate = (RabbitTemplate) bean;
            rabbitTemplate.addBeforePublishPostProcessors(new SendLogMessagePostProcessor());
        }
        return bean;
    }
}
