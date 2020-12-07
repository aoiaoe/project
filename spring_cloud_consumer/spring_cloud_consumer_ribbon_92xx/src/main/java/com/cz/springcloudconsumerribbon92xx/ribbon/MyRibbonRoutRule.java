package com.cz.springcloudconsumerribbon92xx.ribbon;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义Ribbon路由策略:
 *  每个可达的服务访问都访问5次之后再还下一个服务
 *  例如:provder共三个服务, provider_9100 访问五次之后，换成访问provider_9101
 * @author alian
 * @date 2020/10/13 下午 4:53
 * @since JDK8
 */
public class MyRibbonRoutRule extends AbstractLoadBalancerRule {

    private AtomicInteger serverIndex;
    public MyRibbonRoutRule(){
        serverIndex = new AtomicInteger(0);
    }

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object key) {
        final ILoadBalancer loadBalancer = getLoadBalancer();
        if(loadBalancer == null){
            return null;
        }
        final List<Server> servers = loadBalancer.getReachableServers();
        if(CollectionUtils.isEmpty(servers)){
            return null;
        }
        int indexTemp = serverIndex.getAndIncrement();
        indexTemp = indexTemp / 5;
        indexTemp = indexTemp % servers.size();
        return servers.get(indexTemp);
    }
}