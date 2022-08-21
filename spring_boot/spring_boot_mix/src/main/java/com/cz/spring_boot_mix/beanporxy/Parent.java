package com.cz.spring_boot_mix.beanporxy;

public interface Parent {

    default void name(){
        System.out.println("parent");
    }
}
