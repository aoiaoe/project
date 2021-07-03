package com.cz.springcloudconsumerribbon92xx.contoller;

import com.cz.springcloud.entity.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 使用ribbon对user微服务发起http请求
 */
@RequestMapping(value = "/user")
@RestController
public class UserRibbonController {

    // ribbonRestTemplate结合注册中心开启了负载均衡,可以通过服务名对服务提供者进行访问
    private String USER_HTTP_URL = "http://user/user/";

    /**
     * 开启了负载均衡的restTemplate
     */
    @Autowired
    private RestTemplate ribbonLBRestTemplate;

    @GetMapping(value = "/{id}")
    public Entity getById(@PathVariable("id") Integer id) {
        return ribbonLBRestTemplate.getForObject(USER_HTTP_URL + id, Entity.class);
    }

}
