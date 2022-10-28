package com.cz.spring_cloud_alibaba.service.stream;

import com.cz.spring_cloud_alibaba.domain.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;

/**
 * @author jzm
 * @date 2022/10/28 : 16:22
 */
@Slf4j
//@EnableBinding(Sink.class) // 默认输入管道配置类
@EnableBinding(UserVoMessageSink.class) // 自定义输入管道配置类
public class UserVoReceiver {


    /**
     * 1、如果bindings中的绑定关系名称使用默认的input，
     *      类上注解应该为@EnableBinding(Sink.class)
     *      方法上注解应该为 @StreamListener(Sink.INPUT)
     *      原因在于，需要使用Sink类声明输出通道， Sink类中常量INPUT="input"
     * 2、如果bindings中的绑定关系名称为自定义
     *      则需要照抄Sink类，自定义输入配置类， 此处例子为 UserVoMessageSink.java
     *      类上配置为 @EnableBinding(UserVoMessageSink.class)
     *      方法上配置为 @StreamListener(UserVoMessageSink.INPUT)
     *
     * @param message
     */
    @StreamListener(UserVoMessageSink.INPUT)
    public void input(Message<UserVo> message){
        log.info("[收到消息] ：{}", message.getPayload());
    }
}
