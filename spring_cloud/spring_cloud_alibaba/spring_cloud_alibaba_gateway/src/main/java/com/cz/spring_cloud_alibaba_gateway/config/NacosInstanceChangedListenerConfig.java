package com.cz.spring_cloud_alibaba_gateway.config;

import com.alibaba.nacos.client.naming.event.InstancesChangeEvent;
import com.alibaba.nacos.common.notify.Event;
import com.alibaba.nacos.common.notify.listener.Subscriber;
import com.alibaba.nacos.common.utils.JacksonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cloud.loadbalancer.config.LoadBalancerCacheAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

import static org.springframework.cloud.loadbalancer.core.CachingServiceInstanceListSupplier.SERVICE_INSTANCE_CACHE_NAME;

/**
 * 自定义代码实时感知服务上下线，更新loadbalancer缓存
 * 另一种方式为使用配置， 配置spring.cloud.loadbalancer.nacos.enabled: true
 * @author jzm
 * @date 2022/12/8 : 17:26
 */
@Configuration
@AutoConfigureAfter(LoadBalancerCacheAutoConfiguration.class)
@Slf4j
public class NacosInstanceChangedListenerConfig  {


    @Resource
    private CacheManager defaultLoadBalancerCacheManager;

    @Bean
    NacosInstanceChangedListener listener(){
        return new NacosInstanceChangedListener(defaultLoadBalancerCacheManager);
    }


    @AllArgsConstructor
    @Slf4j
    public static class NacosInstanceChangedListener extends Subscriber<InstancesChangeEvent> {

        private CacheManager caffeineLoadBalancerCacheManager;

        @Override
        public void onEvent(InstancesChangeEvent event) {
            log.info("Spring Gateway 接收实例刷新事件：{}, 开始刷新缓存", JacksonUtils.toJson(event));
            Cache cache = caffeineLoadBalancerCacheManager.getCache(SERVICE_INSTANCE_CACHE_NAME);
            if (cache != null) {
                cache.evict(event.getServiceName());
            }
            log.info("Spring Gateway 实例刷新完成");

//        作者：和耳朵
//        链接：https://juejin.cn/post/7152044620015206407/
//        来源：稀土掘金
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
        }

        @Override
        public Class<? extends Event> subscribeType() {
            return InstancesChangeEvent.class;
        }
    }
}
