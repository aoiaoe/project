package com.cz.spring_cloud_alibaba.controller;

import com.cz.spring_cloud_alibaba.domain.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jzm
 * @date 2022/10/13 : 16:53
 */
@RequestMapping(value = "/stream")
@RestController
public class StreamController {

    @Autowired
    private StreamBridge streamBridge;

    @GetMapping
    public void send(){
        UserVo user = new UserVo(1L, "send stream msg", null);
        this.streamBridge.send("userVoMessage", user);
    }
}
