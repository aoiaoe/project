package com.cz.spring_cloud_alibaba_gateway.service;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import javax.annotation.processing.SupportedAnnotationTypes;
import java.util.Collection;
import java.util.List;

/**
 * 动态配置路由信息更新操作
 */
@Slf4j
@SuppressWarnings("all")
@Service
public class DynamicRouteService implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    /** 获取路由定义 */
    private final RouteDefinitionLocator routeDefinitionLocator;
    /** 写路由定义*/
    private final RouteDefinitionWriter routeDefinitionWriter;
    /** 时间发布对象*/
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public DynamicRouteService(RouteDefinitionWriter writer, RouteDefinitionLocator locator){
        this.routeDefinitionLocator = locator;
        this.routeDefinitionWriter = writer;
    }

    /** 增加路由定义*/
    public String addRoutDefinition(RouteDefinition definition){
        log.info("[动态路由] 增加路由定义 :{}", definition);
        // 保存路由配置并发布
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        // 发布事件通知给Gateway， 同步新增的路由定义
        applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));

        return "success";
    }

    /**
     * 更新路由
     * @param routes
     * @return
     */
    public String updateRouteList(List<RouteDefinition> routes){
        if(CollectionUtils.isEmpty(routes)){
            return "success";
        }
        log.info("[动态路由] 更新:{}", JSON.toJSONString(routes));
        // 获取现存的所有定义
        List<RouteDefinition> routeDefinitions = routeDefinitionLocator.getRouteDefinitions().buffer().blockFirst();
        if(!CollectionUtils.isEmpty(routeDefinitions)){
            routeDefinitions.forEach(e -> {
                this.deleteById(e.getId());
            });
        }
        // 将最新的路由同步到gateway
        routes.forEach(e -> {
            this.updateByRouteDefinition(e);
        });
        return "success";
    }

    /** 删除路由 */
    private String deleteById(String id){
        try {
            log.info("[动态路由] 删除路由, id:{}", id);
            routeDefinitionWriter.delete(Mono.just(id)).subscribe();
            this.applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
            return "delete success";
        }catch (Exception e){
            log.error("[动态路由] 删除路由出错:{}", e.getMessage(), e);
            return "delete fail";
        }
    }

    /**
     * 更新路由
     * @param routeDefinition
     * @return
     */
    private String updateByRouteDefinition(RouteDefinition routeDefinition){
        try {
            log.info("[动态路由] 更新路由:{}", routeDefinition);
            this.routeDefinitionWriter.delete(Mono.just(routeDefinition.getId()));
        }catch (Exception e){
            return "update fail delete by id error, id :" + routeDefinition.getId();
        }

        try {
            this.routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
            this.applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
        }catch (Exception e){
            return "route update fail";
        }
        return "success";
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
