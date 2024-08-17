package com.cz.spring_boot_rabbitmq.delayqueue.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.util.StringUtils;

/**
 * @author jy
 */
@Slf4j
public class SendLogMessagePostProcessor implements MessagePostProcessor {

    @Override
    public Message postProcessMessage(Message message) throws AmqpException {
        log.debug("send {}", message);
        if (!StringUtils.isEmpty(TenantDataSourceNameHolder.get())) {
            message.getMessageProperties().getHeaders().put("poolName", TenantDataSourceNameHolder.get());
        }
        message.getMessageProperties().getHeaders().put("haha", "SendLogMessagePostProcessor add extra info hehe" + System.currentTimeMillis());
        return message;
    }

}
