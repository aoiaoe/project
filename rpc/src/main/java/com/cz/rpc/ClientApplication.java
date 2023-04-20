package com.cz.rpc;

import com.cz.rpc.client.IDemoService;

/**
 * @author jzm
 * @date 2023/4/20 : 17:45
 */
public class ClientApplication {

    public static void main(String[] args) {
        RpcClient client = new RpcClient();
        IDemoService demoService = client.proxy(IDemoService.class, "127.0.0.1", 12345);
        System.out.println(demoService.add(1 ,3));
        System.out.println(demoService.sayHi("world!"));
    }
}
