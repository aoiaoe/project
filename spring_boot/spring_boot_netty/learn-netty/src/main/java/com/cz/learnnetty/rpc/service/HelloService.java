package com.cz.learnnetty.rpc.service;

public interface HelloService {

    String hello(String name);

    Integer add(Integer x, Integer y);

    Dish makeFood(Ingredients ing1, Ingredients ing2);
}
