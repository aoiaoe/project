package com.cz.grpcserver.service;

import com.cz.grpc.GreeterGrpc;
import com.cz.grpc.GreeterOuterClass;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.springboot.autoconfigure.grpc.server.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@GrpcService(MyGreeterGrpc.class)
public class MyGreeterGrpc extends GreeterGrpc.GreeterImplBase {

    @Value("${server.port}")
    private int port;

    @Override
    public void sayHello(GreeterOuterClass.HelloRequest request, StreamObserver<GreeterOuterClass.HelloReply> responseObserver) {
        String msg = "Hello from " + port + " " + request.getName();
        GreeterOuterClass.HelloReply.Builder response = GreeterOuterClass.HelloReply.newBuilder().setMessage(msg);
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
        log.info("返回消息:{}", msg);
    }
}
