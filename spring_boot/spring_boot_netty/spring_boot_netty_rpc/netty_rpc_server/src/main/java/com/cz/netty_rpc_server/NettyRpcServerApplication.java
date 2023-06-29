package com.cz.netty_rpc_server;

import com.cz.netty_rpc_server.server.RpcServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyRpcServerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(NettyRpcServerApplication.class, args);
    }

    @Autowired
    private RpcServer rpcServer;

    @Override
    public void run(String... args) throws Exception {
        new Thread(() -> {
            rpcServer.startServer("127.0.0.1", 8899);
        }).start();
    }
}
