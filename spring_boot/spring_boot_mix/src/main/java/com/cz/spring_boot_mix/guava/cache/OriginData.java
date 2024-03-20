package com.cz.spring_boot_mix.guava.cache;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
public class OriginData {

    private HashMap<String, Object> hm = new HashMap<String, Object>() {{
        put("k1", "v1");
        put("k2", "v2");
        put("k3", "v3");
        put("k4", "v4");
    }};

    public Object get(String key) throws InterruptedException {
        log.info("正在回源获取数据");
        TimeUnit.SECONDS.sleep(1);
        return hm.get(key);
    }
}
