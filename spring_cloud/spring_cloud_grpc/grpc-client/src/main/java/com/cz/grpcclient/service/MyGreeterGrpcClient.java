package com.cz.grpcclient.service;

import com.cz.grpc.GreeterGrpc;
import com.cz.grpc.GreeterOuterClass;
import io.grpc.Channel;
import lombok.extern.slf4j.Slf4j;
import net.devh.springboot.autoconfigure.grpc.client.GrpcClient;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MyGreeterGrpcClient {

//    @GrpcClient("grpc-client-demo")
    @GrpcClient("nacos-grpc-demo")
    public Channel channel;

    public String greet(String msg){
        GreeterGrpc.GreeterBlockingStub stub = GreeterGrpc.newBlockingStub(channel);
        GreeterOuterClass.HelloReply reply = stub.sayHello(GreeterOuterClass.HelloRequest.newBuilder().setName(msg).build());
        return reply.getMessage();
    }

}
