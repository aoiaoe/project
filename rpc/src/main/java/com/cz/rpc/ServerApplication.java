package com.cz.rpc;

import com.cz.rpc.client.IDemoService;
import com.cz.rpc.server.DemoServiceImpl;

/**
 * @author jzm
 * @date 2023/4/20 : 17:39
 */
public class ServerApplication {

    public static void main(String[] args) {
        RpcServer rpcServer = new RpcServer();
        rpcServer.addHandler(IDemoService.class.getName(), new DemoServiceImpl());
        rpcServer.export(12345);

    }
}
