package com.cz.stream;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class List2Map {

    private List<TestEntity> entities = new ArrayList<>();
    @Before
    public void setup(){
        entities.add(new TestEntity(1, "a", "zs"));
        entities.add(new TestEntity(2, "a", "ls"));
        entities.add(new TestEntity(3, "b", "ww"));
        entities.add(new TestEntity(3, "b", "ww"));
//        entities.add(new TestEntity(5, null, "lq"));
    }

    /**
     * 将List中元素 根据某个key分组到多个list并加入map
     */
    @Test
    public void test1(){
        Map<String, List<TestEntity>> collect = entities.stream().collect(Collectors.groupingBy(e -> e.getAid()));
        collect.entrySet().forEach(e -> {
            System.out.println(e.getKey() + " -> " + e.getValue());
        });
    }

    @Test
    public void testDistinct(){
        List<Integer> integers = Arrays.asList(1, 2, 3, 3, 2, 4);
        System.out.println(integers.toString());
        integers = integers.stream().distinct().collect(Collectors.toList());
        System.out.println(integers.toString());
        entities = entities.stream().distinct().collect(Collectors.toList());
        System.out.println(entities);
    }
    
}
