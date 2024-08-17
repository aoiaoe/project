package com.cz.collection;

import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author alian
 * @date 2020/9/17 下午 2:47
 * @since JDK8
 */
public class HashMapDemo {

    public static void main(String[] args) throws Exception {
        int x = -1 << 29;
        System.out.println(Integer.toBinaryString(-1));
        System.out.println(Integer.toBinaryString(1));
        System.out.println(Integer.toBinaryString(-5));
        System.out.println(Integer.toBinaryString(5));
        System.out.println(Integer.toBinaryString(x));
        Map map = new HashMap(6);
        map.put("1", "1");
//        testHash();
    }

    /**
     * 1、树化,前提条件:1、一个桶中元素 > 8个,2、Map中元素总数 > 64
     * 如果桶中元素>8,但是总元素 < 64,这时只会扩容,扩容之后,大于8的桶几率几乎为0
     * 2、退树化,如果树上元素小于<=6,则会退树化为链表
     */
    public static void testHash() throws Exception {
        HashMap<Integer, Integer> map = new HashMap<>(3);
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(17);
        integers.add(33);
        integers.add(49);
        integers.add(65);
        integers.add(81);
        integers.add(97);
        integers.add(113);
        integers.add(129);
        integers.add(145);
        integers.add(161);
        final Class<? extends HashMap> aClass = map.getClass();
        Field f = aClass.getDeclaredField("table");
        f.setAccessible(true);
//        for (Integer integer : integers) {
        for (int integer = 0; integer < 20; integer++) {

            map.put(integer, integer);
            Object[] o = (Object[]) f.get(map);
            System.out.println(integer + "\t" + o.length);
//            int h;
//            System.out.println((h = integer.hashCode()) ^ (h >>> 16));
        }
        System.out.println("==============");
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }
    }

    public static void testConcurrentHash() throws Exception {
        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>(16, 0.5f);
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(64);
        integers.add(129);
        integers.add(193);
        integers.add(257);
        integers.add(321);
        integers.add(385);
        integers.add(449);
        integers.add(513);
        integers.add(577);
        integers.add(641);
        final Class<? extends ConcurrentHashMap> aClass = map.getClass();
        Field f = aClass.getDeclaredField("table");
        f.setAccessible(true);
        for (Integer integer : integers) {
            map.put(integer, integer);
            Object[] o = (Object[]) f.get(map);
            System.out.println(integer + "\t" + o.length);
        }
        System.out.println("==============");
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }
    }


    public static void testTreeMap() {
        Map<String, String> treeMap = new TreeMap<>();

        treeMap.put("1", "1");
        System.out.println(treeMap);
        treeMap.put("1", "2");
        System.out.println(treeMap);
    }

    /**
     * 本意是代理hashmap方法，但无用，map内部方法不走代理
     * 要想实现，需要使用agent或者静态代理从字节码方面去实现
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static void proxy() throws NoSuchFieldException, IllegalAccessException {
        HashMap<Integer, Integer> map = new HashMap<>(16, 0.5f);
        HashMapCglibProxy<HashMap<Integer, Integer>> proxy = new HashMapCglibProxy(map);
        map = proxy.getProxy();

        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(17);
        integers.add(33);
        integers.add(49);
        integers.add(65);
        integers.add(81);
        integers.add(97);
        integers.add(113);
        integers.add(129);
        integers.add(145);
        integers.add(161);
//        final Class<? extends HashMap> aClass = map.getClass();
//        Field f = aClass.getDeclaredField("table");
//        f.setAccessible(true);
        for (Integer integer : integers) {
            map.put(integer, integer);
//            Object[] o = (Object[]) f.get(map);
//            System.out.println(integer + "\t" + o.length);
            System.out.println(integer);
//            int h;
//            System.out.println((h = integer.hashCode()) ^ (h >>> 16));
        }
        System.out.println("==============");
//        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
//            System.out.println(entry.getKey() + "\t" + entry.getValue());
//        }

    }

}
