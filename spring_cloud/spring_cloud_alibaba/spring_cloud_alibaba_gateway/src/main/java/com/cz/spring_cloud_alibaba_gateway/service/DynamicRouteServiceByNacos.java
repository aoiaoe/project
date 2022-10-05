package com.cz.spring_cloud_alibaba_gateway.service;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.cz.spring_cloud_alibaba_gateway.config.GatewayConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;

import static com.alibaba.nacos.api.PropertyKeyConst.*;
import static com.alibaba.nacos.api.PropertyKeyConst.PASSWORD;

/**
 * 通过nacos配置中心下发路由配置，然后更新gateway路由
 */
@Slf4j
@Component
@SuppressWarnings("all")
public class DynamicRouteServiceByNacos {

    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;
    @Autowired
    private GatewayConfig gatewayConfig;
    /**
     * nacos 配置服务客户端
     */
    private ConfigService configService;



    @Autowired
    private DynamicRouteService dynamicRouteService;

    private ConfigService initConfigService() {
        try {
            final Properties properties = nacosDiscoveryProperties.getNacosProperties();
            final ConfigService configService = NacosFactory.createConfigService(properties);
            return configService;
        } catch (Exception e) {
            log.error("[连接配置中心] 错误:{}", e);
            return null;
        }
    }

    @PostConstruct
    public void init(){
        try {
            log.info("初始化路由定义");
            configService = initConfigService();
            if (configService == null) {
                log.error("[动态路由] 初始化路由配置出错");
                return;
            }
            // 通过指定nacos配置的路由路径去获取配置
            final String config = this.configService.getConfig(gatewayConfig.getDataId(), gatewayConfig.getGroup(), gatewayConfig.getTimeOut());
            if(StringUtils.isEmpty(config)){
                log.error("[动态路由] 初始化gateway获取路由配置为空");
                return;
            }
            // 初始化路由
            List<RouteDefinition> routeDefinitions = JSON.parseArray(config, RouteDefinition.class);
            this.dynamicRouteService.updateRouteList(routeDefinitions);
            // 监听路由配置变更，并更新路由
            dynamicRouteByNacosLisenter(gatewayConfig.getDataId(), gatewayConfig.getGroup());
        }catch (Exception e){

        }
    }

    /**
     * 监听naocs下发的动态路由配置信息
     *
     * @param dataId
     * @param group
     */
    private void dynamicRouteByNacosLisenter(String dataId, String group) {
        try {
            this.configService.addListener(dataId, group, new Listener() {
                /**
                 * 提供线程池
                 * @return
                 */
                @Override
                public Executor getExecutor() {
                    return null;
                }

                /**
                 * 接收到配置的变更信息
                 * @param configInfo
                 */
                @Override
                public void receiveConfigInfo(String configInfo) {
                    final List<RouteDefinition> routeDefinitions = JSON.parseArray(configInfo, RouteDefinition.class);
                    dynamicRouteService.updateRouteList(routeDefinitions);
                }
            });
        }catch (NacosException ne){
            ne.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
