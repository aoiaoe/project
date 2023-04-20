package com.cz.rpc.server;

import com.cz.rpc.client.IDemoService;

/**
 * @author jzm
 * @date 2023/4/20 : 17:14
 */
public class DemoServiceImpl implements IDemoService {
    @Override
    public int add(int a, int b) {
        return a + b;
    }

    @Override
    public String sayHi(String name) {
        return "hello " + name;
    }
}
