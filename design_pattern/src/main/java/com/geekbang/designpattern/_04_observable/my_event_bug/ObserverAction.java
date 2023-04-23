package com.geekbang.designpattern._04_observable.my_event_bug;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 将注册的观察者解析成下列结构
 * @author jzm
 * @date 2023/4/23 : 14:16
 */
@Slf4j
public class ObserverAction {

    private Object target;
    private Method method;

    public ObserverAction(Object target, Method method){
        this.target = target;
        this.method = method;
        this.method.setAccessible(true);
    }

    /**
     * 执行观察者的方法
     * @param event
     */
    public void execute(Object event){
        try {
            log.info("指定观察者方法:{}", method.getName());
            this.method.invoke(target, event);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
