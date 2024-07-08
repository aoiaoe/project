package com.cz.springbootredis.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CacheObjHolder {

    public static Map<Long, CacheObj> holders = new HashMap<>();
    static {
        Random random = new Random();
        for (long i = 1; i < 10; i++) {
            holders.put(i, new CacheObj(i, "name" + i, random.nextInt(50) + 10));
        }
    }
}
