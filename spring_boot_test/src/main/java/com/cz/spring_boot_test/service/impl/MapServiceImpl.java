package com.cz.spring_boot_test.service.impl;

import com.cz.spring_boot_test.service.MapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class MapServiceImpl implements MapService {

    Map<String, String> map = new ConcurrentHashMap<>();

    @PostConstruct
    public void init(){
        for (int i = 0; i < 10; i++) {
            log.info("map.put..................");
            map.put(i + "", "String_" + i);
        }
    }

    @Override
    public String getValue(String key) {
        log.info("param:{}", key);
        return map.get(key);
    }

}
