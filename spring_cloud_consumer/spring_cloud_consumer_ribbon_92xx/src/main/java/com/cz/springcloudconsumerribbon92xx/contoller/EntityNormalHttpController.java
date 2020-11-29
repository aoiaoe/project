package com.cz.springcloudconsumerribbon92xx.contoller;

import com.cz.springcloud.entity.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author alian
 * @date 2020/10/12 下午 6:06
 * @since JDK8
 */
@RequestMapping(value = "/entity")
@RestController
public class EntityNormalHttpController {

    // restTemplate只是一个单纯的http客户端,只能通过ip:port对服务提供访问
    private String HTTP_URL = "http://localhost:9100/entity/";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/{id}")
    public Entity getById(@PathVariable("id") Integer id){
        return restTemplate.getForObject(HTTP_URL + id, Entity.class);
    }

    @GetMapping
    public List<Entity> listAll(){
        ParameterizedTypeReference<List<Entity>> type = new ParameterizedTypeReference<List<Entity>>(){};
        ResponseEntity<List<Entity>> exchange = restTemplate.exchange(HTTP_URL + "list", HttpMethod.GET, null, type);
        return exchange.getBody();
    }

}
