package com.geekbang.rpc.demo.server;


import com.geekbang.rpc.RpcServer;
import com.geekbang.rpc.demo.client.CalculatorService;

public class RpcServerApplication {

    public static void main(String[] args) throws Exception {
      CalculatorService service = new CalculatorServiceImpl();
      RpcServer server = new RpcServer();
      server.export(service, 1234);
    }

}
