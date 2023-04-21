package com.geekbang.designpattern._04_observable;

import com.geekbang.designpattern._04_observable.observer.UserObserver;

import java.util.List;

/**
 * 通过框架自动注入观察者，实现观察者模式
 * @author jzm
 * @date 2023/4/21 : 17:49
 */
public class UserServiceImpl {

    // 通过依赖注入注入容器中所有的用户观察者
//    @Autowired
    private List<UserObserver> observers;

    public void register(Long userUd){
        // 业务逻辑

        // 通知观察者
        for (UserObserver observer : observers) {
            observer.regSuccess(userUd);
        }
    }
}
