package com.cz.spring_boot_rabbitmq.stream;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.lang.reflect.Method;

@ToString
@AllArgsConstructor
@lombok.Data
public class Data implements Serializable {

    private String name;

    private Integer age;

    private void sayHi(){
        System.out.println("say hi !!!!");
    }

    public static void main(String[] args) throws NoSuchMethodException {
        Data data = new Data("cz", 1);
        Class<? extends Data> clazz = data.getClass();
        Method sayHi = clazz.getMethod("sayHi");
        sayHi.setAccessible(true);
    }
}
