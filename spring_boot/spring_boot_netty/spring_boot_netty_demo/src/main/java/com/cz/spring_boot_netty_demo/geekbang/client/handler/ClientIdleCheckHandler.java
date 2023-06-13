package com.cz.spring_boot_netty_demo.geekbang.client.handler;

import io.netty.handler.timeout.IdleStateHandler;

public class ClientIdleCheckHandler extends IdleStateHandler {
    public ClientIdleCheckHandler(){
        super(0,  5 , 1);
    }
}
