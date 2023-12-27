package com.cz.spring_boot_mix.contoller.bean;

import lombok.Data;

@Data
public class MyBean {

    private int a = 1;

    private int b = 2;

    private BeanA beanA = new BeanA();
}

@Data
class BeanA{

    private int c = 3;

    private BeanB beanB = new BeanB();
}

@Data
class BeanB{

    private int d = 4;

    private BeanC beanC = new BeanC();
}

@Data
class BeanC{

    private int  e = 5;

}

