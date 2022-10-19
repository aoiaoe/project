package com.cz.spring_cloud_alibaba.controller;

import com.alibaba.fastjson.JSON;
import com.cz.spring_cloud_alibaba.domain.UserVo;
import com.cz.spring_cloud_alibaba.hystrix.UserServiceHystrixCommand;
import com.cz.spring_cloud_alibaba.hystrix.UserServiceObservableCommand;
import com.cz.spring_cloud_alibaba.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;
import rx.Observer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author jzm
 * @date 2022/10/13 : 14:50
 */
@Slf4j
@RequestMapping(value = "/hystrix")
@RestController
public class HystrixController {

    @Autowired
    private UserService userService;

    /**
     * @param id
     * @return
     */
    @GetMapping(value = "/hystrix-command")
    public UserVo user(Long id) throws ExecutionException, InterruptedException {
        // // 同步阻塞
        UserServiceHystrixCommand command1 = new UserServiceHystrixCommand(userService, id);
        UserVo execute = command1.execute();
        log.info("第一种方式 同步阻塞{}", execute);

        // 异步非阻塞
        UserServiceHystrixCommand command2 = new UserServiceHystrixCommand(userService, id);
        Future<UserVo> future = command2.queue();
        // 通过一部非阻塞调用hystrix 可以让出cpu做些其他事
        log.info("第二种方式 异步非阻塞 {}", future.get());

        /**
         * // 热响应调用
         */
        UserServiceHystrixCommand command3 = new UserServiceHystrixCommand(userService, id);
        UserVo single = command3.observe().toBlocking().single();
        log.info("第三种方式 热响应调用 {}", single);

        /**
         * // 异步冷响应调用
         */
        UserServiceHystrixCommand command4 = new UserServiceHystrixCommand(userService, id);
        UserVo single1 = command4.toObservable().toBlocking().single();
        log.info("第四种方式 异步冷响应调用 {}", single1);
        return null;
    }

    @GetMapping("/hystrix-observable-command")
    public void getServiceInstancesByServiceIdObservable(Long id) {

        UserServiceObservableCommand observableCommand =
                new UserServiceObservableCommand(userService, id);

        // 异步执行命令
        Observable<UserVo> observe = observableCommand.observe();
        AtomicReference<UserVo> userVoAtomicReference = new AtomicReference<>();
        // 注册获取结果
        observe.subscribe(
                new Observer<UserVo>() {

                    // 执行 onNext 之后再去执行 onCompleted
                    @Override
                    public void onCompleted() {
                        log.info("all tasks is complete: [{}], [{}]",
                                id, Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(UserVo userVo) {
                        userVoAtomicReference.set(userVo);
                    }
                }
        );

        log.info("observable command result is : [{}], [{}]",
                JSON.toJSONString(userVoAtomicReference.get()), Thread.currentThread().getName());
    }
}
