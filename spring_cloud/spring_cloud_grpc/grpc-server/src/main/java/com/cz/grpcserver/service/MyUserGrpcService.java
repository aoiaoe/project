package com.cz.grpcserver.service;

import com.cz.grpc.UsersGrpc;
import com.cz.grpc.UsersOuterClass;
import io.grpc.stub.StreamObserver;
import net.devh.springboot.autoconfigure.grpc.server.GrpcService;

import java.util.Random;

@GrpcService(MyUserGrpcService.class)
public class MyUserGrpcService extends UsersGrpc.UsersImplBase {

    @Override
    public void getUserInfo(UsersOuterClass.GetUserInfoRequest request, StreamObserver<UsersOuterClass.GetUserInfoResponse> responseObserver) {
        Random r = new Random();
        UsersOuterClass.GetUserInfoResponse response = UsersOuterClass.GetUserInfoResponse.newBuilder()
                .setId(request.getId())
                .setName("Jzm" + r.nextInt(100))
                .setAge(r.nextInt(80) + 15)
                .setSex(r.nextInt(2) % 2 == 0 ? "男" : "女").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
