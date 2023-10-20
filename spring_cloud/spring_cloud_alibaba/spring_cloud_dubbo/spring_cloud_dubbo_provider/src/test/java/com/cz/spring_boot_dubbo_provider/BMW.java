package com.cz.spring_boot_dubbo_provider;

import org.apache.dubbo.common.URL;

public class BMW implements Car{
    @Override
    public void drive(URL url) {
        System.out.println("BMW is Driving");
    }
}
