package com.cz.spring_cloud_alibaba.service.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface UserVoMessageSource {


    /**
     * 该值应该为bindings中的绑定关系的名称
     */
    String OUTPUT = "userVoOutput";

    /**
     * @return output channel
     */
    @Output(UserVoMessageSource.OUTPUT)
    MessageChannel userVoOutput();
}
