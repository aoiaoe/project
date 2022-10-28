package com.cz.spring_cloud_alibaba.service.stream;

import com.cz.spring_cloud_alibaba.domain.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

/**
 * @author jzm
 * @date 2022/10/28 : 15:17
 */
//@EnableBinding(Source.class) // 默认消息发送配置
@EnableBinding(UserVoMessageSource.class) // 自定义消息发送配置
public class UserVoSender {

    /**
     * 此bean的名称对应 bindings下面的绑定关系的名称，该名称可以自定义
     * 1、如果不自定义，可以使用默认的output，则根据上面的规则bean名称为output，
     *      并且应该使用默认消息发送配置 @EnableBinding(Source.class)
     *      原因在于 Source类中OUTPUT常量，定义了发送管道的名称
     * 2、如果使用了自定义的绑定关系的名称，则根据上面的规则bean名称为自定义名称
     *      并且需要照抄Source类自定义source类，此处为 UserVoMessageSource
     *
     */
    @Autowired
    private MessageChannel userVoOutput;

    public void send(UserVo msg){
        userVoOutput.send(MessageBuilder.withPayload(msg).build());
    }

}
