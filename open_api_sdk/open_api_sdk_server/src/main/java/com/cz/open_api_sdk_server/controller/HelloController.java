package com.cz.open_api_sdk_server.controller;

import com.cz.open_api_sdk_server.config.MyParam;
import com.cz.open_api_sdk_server.dto.HelloDto;
import com.cz.open_api_sdk_server.vo.HelloVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jzm
 * @date 2022/6/6 : 14:05
 */
@RequestMapping(value = "/api/v1")
@RestController
public class HelloController {

    @PostMapping(value = "/hello")
    public HelloVo hello(@RequestBody HelloDto param){
        return new HelloVo("hello " + param.getName(), param.getChaos());
    }
}
