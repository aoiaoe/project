package com.cz.spring_boot_rabbitmq.delayqueue.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;

import java.util.Objects;

/**
 * @author jy
 */
@Slf4j
public class LogMessagePostProcessor implements MessagePostProcessor {

    @Override
    public Message postProcessMessage(Message message) throws AmqpException {
        log.debug("{}", message);
        Object agentId = message.getMessageProperties().getHeader("poolName");
        if (!Objects.isNull(agentId)) {
            TenantDataSourceNameHolder.set((String) agentId);
        }
        return message;
    }

}
