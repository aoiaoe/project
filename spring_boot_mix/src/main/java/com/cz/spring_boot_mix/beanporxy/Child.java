package com.cz.spring_boot_mix.beanporxy;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class Child extends ParentClass implements Parent{

//    @Async
    @Override
    public void name() {
        System.out.println("child");
    }

    @Async
    @Override
    public void age() {
        System.out.println("child class : " + 5);
    }
}
