package com.geekbang.designpattern._04_observable.my_event_bug;

import com.geekbang.designpattern._04_observable.my_event_bug.anno.Subscribe;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author jzm
 * @date 2023/4/23 : 14:25
 */
@Slf4j
public class ObserverRegistry {

    private ConcurrentHashMap<Class<?>, CopyOnWriteArraySet<ObserverAction>> registeredObserver = new ConcurrentHashMap<>();

    /**
     * 将观察者类中的所有观察者方法注册进缓存中
     * @param observer
     */
    public void register(Object observer){
        Map<Class<?>, CopyOnWriteArraySet<ObserverAction>> actions = this.findAllObserverActions(observer);
        for (Map.Entry<Class<?>, CopyOnWriteArraySet<ObserverAction>> entry : actions.entrySet()) {
            Class<?> eventType = entry.getKey();
            Collection<ObserverAction> observerMethod = entry.getValue();
            CopyOnWriteArraySet<ObserverAction> observerActions = registeredObserver.get(eventType);
            if(observerActions == null){
                observerActions = new CopyOnWriteArraySet<ObserverAction>();
                registeredObserver.putIfAbsent(eventType, observerActions);
            }
            observerActions.addAll(observerMethod);
        }
    }


    public List<ObserverAction> getMethodObserverActions(Object event){
        List<ObserverAction> actions = new ArrayList<>();
        Class<?> eventClass = event.getClass();
        for (Map.Entry<Class<?>, CopyOnWriteArraySet<ObserverAction>> entry : registeredObserver.entrySet()) {
            if(entry.getKey().isAssignableFrom(eventClass)){
                actions.addAll(entry.getValue());
            }
        }
        return actions;
    }



    private Map<Class<?>, CopyOnWriteArraySet<ObserverAction>> findAllObserverActions(Object observer){
        Map<Class<?>, CopyOnWriteArraySet<ObserverAction>> actions = new HashMap<>();
        Class<?> observerClass = observer.getClass();
        for (Method method : getSubscribeMethod(observerClass)) {
            Class<?> parameterType = method.getParameterTypes()[0];
            if(!registeredObserver.contains(parameterType)){
                registeredObserver.put(parameterType, new CopyOnWriteArraySet<ObserverAction>());
            }
            registeredObserver.get(parameterType).add(new ObserverAction(observer, method));
        }

        return actions;
    }

    /**
     * 获取带有@Subscribe注解的方法
     * @param clz
     * @return
     */
    private List<Method> getSubscribeMethod(Class<?> clz){
        List<Method> annotatedMethods = new ArrayList<>();
        for (Method declaredMethod : clz.getDeclaredMethods()) {
            if(declaredMethod.isAnnotationPresent(Subscribe.class)){
                // 校验方法的参数只能有一个
                if(declaredMethod.getParameterCount() != 1){
                    log.error("Observers' param must be 1! class:{}, method:{}", clz, declaredMethod);
                    throw new RuntimeException("Observers' param must be 1!");
                }
                annotatedMethods.add(declaredMethod);
            }
        }
        return annotatedMethods;
    }
}
