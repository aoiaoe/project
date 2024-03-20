package com.cz.rocket.service;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class IdUtils {


    private AtomicLong longAdder = new AtomicLong();

    {
        longAdder.set(100000);
    }

    public Long id(){
        return longAdder.incrementAndGet();
    }
}
