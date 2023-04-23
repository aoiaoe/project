package com.geekbang.designpattern._04_observable.my_event_bug;

import java.util.List;

public class EventBus {

    protected ObserverRegistry registry;
    public EventBus(){
        registry = new ObserverRegistry();

    }

    public void register(Object observer) {
        registry.register(observer);
    }

    public void post(Object event) {
        List<ObserverAction> methodObserverActions = registry.getMethodObserverActions(event);
        for (ObserverAction action : methodObserverActions) {
            action.execute(event);
        }
    }
}
