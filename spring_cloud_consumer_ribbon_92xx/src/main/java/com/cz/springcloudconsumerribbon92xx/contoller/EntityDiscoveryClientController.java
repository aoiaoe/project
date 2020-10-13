package com.cz.springcloudconsumerribbon92xx.contoller;

import com.cz.springcloud.entity.Entity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;

/**
 * @author alian
 * @date 2020/10/13 上午 10:30
 * @since JDK8
 */
@Slf4j
@RequestMapping(value = "entityDiscoveryClient")
@RestController
public class EntityDiscoveryClientController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping(value = "/{id}")
    public Entity getById(@PathVariable("id") Integer id){
        String url = choose();
        if(url == null){
            return null;
        }
        return restTemplate.getForObject(url + id, Entity.class);
    }

    @GetMapping
    public List<Entity> listAll(){
        String url = choose();
        if(url == null){
            return null;
        }
        ParameterizedTypeReference<List<Entity>> type = new ParameterizedTypeReference<List<Entity>>(){};
        ResponseEntity<List<Entity>> exchange = restTemplate.exchange(url + "list", HttpMethod.GET, null, type);
        return exchange.getBody();
    }

    public String choose(){
        List<ServiceInstance> provider = discoveryClient.getInstances("provider");
        if(CollectionUtils.isEmpty(provider)){
            return null;
        }
        int index = new Random().nextInt(provider.size());
        final ServiceInstance instance = provider.get(index);
        String url =  "http://" + instance.getHost() + ":" + instance.getPort() + "/entity/";
        log.info("choose service:{}", url);
        return url;
    }

}
