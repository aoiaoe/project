package com.cz.spring_cloud_alibaba.hystrix;

import com.cz.spring_cloud_alibaba.domain.UserVo;
import com.cz.spring_cloud_alibaba.service.UserService;
import com.netflix.hystrix.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy.THREAD;

/**
 * 代码配置实现服务保护， 方式1： 继承HystrixCommand<R>
 *
 * @author jzm
 * @date 2022/10/13 : 14:26
 */
@Slf4j
public class UserServiceHystrixCommand extends HystrixCommand<UserVo> {

    /**
     * 需要hystrix保护的服务
     */
    private UserService userService;

    private Long userId;

    public UserServiceHystrixCommand(UserService userService, Long userId) {
        // 线程池隔离配置
        super(
                Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("userService"))
                        .andCommandKey(HystrixCommandKey.Factory.asKey("UserServiceHystrixCommand"))
                        .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("userServicePool"))
                        // 线程池 key 配置
                        .andCommandPropertiesDefaults(
                                HystrixCommandProperties.Setter()
                                        // 线程池隔离策略
                                        .withExecutionIsolationStrategy(THREAD)
                                        // 开启降级
                                        .withFallbackEnabled(true)
                                        // 开启熔断器
                                        .withCircuitBreakerEnabled(true)
                                        .withCircuitBreakerSleepWindowInMilliseconds(5000)
                                        .withCircuitBreakerErrorThresholdPercentage(50)

                        )
                // 配置信号量隔离策略
                // Setter semaphore =
                //         Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("NacosClientService"))
                //         .andCommandKey(HystrixCommandKey.Factory.asKey("NacosClientHystrixCommand"))
                //         .andCommandPropertiesDefaults(
                //                 HystrixCommandProperties.Setter()
                //                 .withCircuitBreakerRequestVolumeThreshold(10)
                //                 .withCircuitBreakerSleepWindowInMilliseconds(5000)
                //                 .withCircuitBreakerErrorThresholdPercentage(50)
                //                  // 指定使用信号量隔离
                //                 .withExecutionIsolationStrategy(SEMAPHORE)
                //         );
        );
        this.userId = userId;
        this.userService = userService;
    }

    @Override
    protected UserVo run() throws Exception {
        log.info("[hystrix保护的服务调用] user service, 线程名:{}", Thread.currentThread().getName());
        return this.userService.user(userId);
    }

    @Override
    protected UserVo getFallback() {
        log.info("[hystrix保护的服务调用] user service, 线程名:{}", Thread.currentThread().getName());
        return new UserVo(null, "hystrix fallback", null);
    }
}
