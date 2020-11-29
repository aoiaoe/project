package com.cz.springcloudconsumerribbon92xx.contoller;

import com.cz.springcloud.entity.Entity;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "/entityRibbon")
@RestController
public class EntityRibbonController {

    // ribbonRestTemplate结合注册中心开启了负载均衡,可以通过服务名对服务提供者进行访问
    private String ENTIiTY_HTTP_URL = "http://provider/entity/";

    /**
     * 开启了负载均衡的restTemplate
     */
    @Autowired
    private RestTemplate ribbonLBRestTemplate;

    @GetMapping(value = "/{id}")
    public Entity getById(@PathVariable("id") Integer id){
        return ribbonLBRestTemplate.getForObject(ENTIiTY_HTTP_URL + id, Entity.class);
    }

    @GetMapping
    public List<Entity> listAll(){
        ParameterizedTypeReference<List<Entity>> type = new ParameterizedTypeReference<List<Entity>>(){};
        ResponseEntity<List<Entity>> exchange = ribbonLBRestTemplate.exchange(ENTIiTY_HTTP_URL + "list", HttpMethod.GET, null, type);
        return exchange.getBody();
    }

}
