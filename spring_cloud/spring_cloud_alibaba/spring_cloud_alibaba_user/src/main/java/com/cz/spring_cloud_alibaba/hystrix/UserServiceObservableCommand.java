package com.cz.spring_cloud_alibaba.hystrix;

import com.cz.spring_cloud_alibaba.domain.UserVo;
import com.cz.spring_cloud_alibaba.service.UserService;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixObservableCommand;
import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Subscriber;

import java.util.Collections;

/**
 * 实现hystrix降级方式2： 继承HystrixObservableCommand<R>
 *     此方式是基于信号量实现
 * @author jzm
 * @date 2022/10/13 : 15:42
 */
@Slf4j
public class UserServiceObservableCommand extends HystrixObservableCommand<UserVo> {

    private UserService userService;
    private Long id;

    public UserServiceObservableCommand(UserService userService, Long id) {
        super(
                Setter
                        .withGroupKey(HystrixCommandGroupKey
                                .Factory.asKey("userServiceHystrixOb"))
                        .andCommandKey(HystrixCommandKey
                                .Factory.asKey("UserServiceObservableCommand"))
                        .andCommandPropertiesDefaults(
                                HystrixCommandProperties.Setter()
                                        .withFallbackEnabled(true)          // 开启降级
                                        .withCircuitBreakerEnabled(true)    // 开启熔断器
                        )
        );
        this.userService = userService;
        this.id = id;
    }

    @Override
    protected Observable<UserVo> construct() {
        // Observable 有三个关键的事件方法, 分别是 onNext、onCompleted、onError
        return Observable.create(new Observable.OnSubscribe<UserVo>() {
            @Override
            public void call(Subscriber<? super UserVo> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        log.info("user service hystrix保护 开始: [{}], [{}]",
                                id,
                                Thread.currentThread().getName());
                        subscriber.onNext(userService.user(id));
                        subscriber.onCompleted();
                        log.info("user service hystrix保护 结束: [{}], [{}]",
                                id,
                                Thread.currentThread().getName());
                    }
                } catch (Exception ex) {
                    subscriber.onError(ex);
                }
            }
        });
    }

    @Override
    protected Observable<UserVo> resumeWithFallback() {
        // Observable 有三个关键的事件方法, 分别是 onNext、onCompleted、onError
        return Observable.create(new Observable.OnSubscribe<UserVo>() {
            @Override
            public void call(Subscriber<? super UserVo> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        log.info("user service hystrix降级 开始: [{}], [{}]",
                                id,
                                Thread.currentThread().getName());
                        subscriber.onNext(new UserVo(null, "hystrix 降级", null));
                        subscriber.onCompleted();
                        log.info("user service hystrix降级 结束: [{}], [{}]",
                                id,
                                Thread.currentThread().getName());
                    }
                } catch (Exception ex) {
                    subscriber.onError(ex);
                }
            }
        });
    }
}
