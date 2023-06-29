package com.cz.netty_client;

import com.cz.netty_client.proxy.RpcProxy;
import com.cz.netty_rpc.api.IUserService;
import com.cz.netty_rpc.pojo.User;

public class ClientBootstrap {

    public static void main(String[] args) {
        IUserService userService = (IUserService)RpcProxy.createProxy(IUserService.class);
        User user = userService.getUserById(1);
        System.out.println(user);
    }
}
