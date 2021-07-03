package com.cz.collection;

import java.util.HashSet;
import java.util.Set;

/**
 * HashSet 底层是一个HashMap, 故往HashSet中存放一个值，其实是往HashMap中放一个key，所有的value为默认的一个Object
 * HashSet拥有和HashMap相似的属性,
 * 因为HashMap的key是唯一的，无序的，并且可以为null(但是只能有一个null，因为多次put一个null键会覆盖)
 * 所以HashSet的值是为唯一的，无序的，可以为null值，但是只能存在一个
 *
 * @author alian
 * @date 2020/11/17 上午 9:46
 * @since JDK8
 */
public class SetDemo {

    public static void main(String[] args) {
        testNullValue();
    }

    public static void testNullValue() {
        Set<String> set = new HashSet<>();
        set.add("1");
        set.add(null);
        System.out.println(set.size());
        set.add(null);
        System.out.println(set.size());
        set.add("2");
        System.out.println(set.size());
        System.out.println(set);
    }


}
