package com.cz.spring_cloud_alibaba.service.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface UserVoMessageSink {

    /**
     * 该值为bindings中的绑定关系的名称
     */
    String INPUT = "userVoInput";

    /**
     * @return input channel.
     */
    @Input(UserVoMessageSink.INPUT)
    SubscribableChannel userVoInput();
}
