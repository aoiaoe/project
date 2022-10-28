package com.cz.spring_cloud_alibaba.controller;

import com.alibaba.nacos.common.utils.UuidUtils;
import com.cz.spring_cloud_alibaba.domain.UserVo;
import com.cz.spring_cloud_alibaba.service.StreamService;
import com.cz.spring_cloud_alibaba.service.stream.UserVoSender;
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
    @Autowired
    private StreamService streamService;

    @Autowired
    private UserVoSender userVoSender;

    @GetMapping
    public void send(){
        for (long i = 0; i < 6; i++) {
            UserVo user = new UserVo(i, "send stream msg" + i, null);
            this.streamBridge.send("userVoMessage", user);
        }
    }

    @GetMapping(value = "/1")
    public void send1(){
        UserVo userVo = new UserVo();
        userVo.setId(1L);
        userVo.setName(UuidUtils.generateUuid());
        this.userVoSender.send(userVo);
    }


}
