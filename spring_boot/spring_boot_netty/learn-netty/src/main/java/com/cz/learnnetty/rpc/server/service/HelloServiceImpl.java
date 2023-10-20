package com.cz.learnnetty.rpc.server.service;

import com.cz.learnnetty.rpc.service.Dish;
import com.cz.learnnetty.rpc.service.HelloService;
import com.cz.learnnetty.rpc.service.Ingredients;

public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String name) {
        return "hello " + name;
    }

    @Override
    public Integer add(Integer x, Integer y) {
        return x + y;
    }

    @Override
    public Dish makeFood(Ingredients ing1, Ingredients ing2) {
        Dish dish = new Dish();
        dish.setFoods(ing1.getName() + " + " + ing2.getName());
        return dish;
    }
}
