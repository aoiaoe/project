package com.cz.spring_boot_mix.actuator.contributor;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.actuate.health.CompositeHealthContributor;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.NamedContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class ThreadPoolContributor implements CompositeHealthContributor {

    private Map<String, HealthContributor> map = new HashMap<>();

    public ThreadPoolContributor(){
        // 此处为演示，正常应该是注入的线程池
        this.map.put("oneSizeQueue", new ThreadPoolIndicator(new ThreadPoolExecutor(1, 1, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1))));
        this.map.put("multiSizeQueue", new ThreadPoolIndicator(new ThreadPoolExecutor(10, 50, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1))));
    }

    @Override
    public HealthContributor getContributor(String name) {
        return map.get(name);
    }

    @NotNull
    @Override
    public Iterator<NamedContributor<HealthContributor>> iterator() {
        return map.entrySet().stream().map(e -> NamedContributor.of(e.getKey(), e.getValue())).iterator();
    }
}
