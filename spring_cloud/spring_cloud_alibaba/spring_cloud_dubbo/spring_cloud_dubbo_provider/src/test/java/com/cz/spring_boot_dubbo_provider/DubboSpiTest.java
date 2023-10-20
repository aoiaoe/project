package com.cz.spring_boot_dubbo_provider;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.junit.jupiter.api.Test;

public class DubboSpiTest {

    @Test
    public void test1(){

        Car car = ExtensionLoader.getExtensionLoader(Car.class).getAdaptiveExtension();
        car.drive(URL.valueOf("http://localhost?car=BMW"));
        car.drive(URL.valueOf("http://localhost?car=BENZ"));

    }

    @Test
    public void test2(){
        Car car = ExtensionLoader.getExtensionLoader(Car.class).getExtension("BENZ");
        car.drive(URL.valueOf("http://localhost?car=BMW"));
    }
}
