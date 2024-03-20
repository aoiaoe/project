package com.cz.grpcclient.service;

import com.cz.grpc.UsersGrpc;
import com.cz.grpc.UsersOuterClass;
import io.grpc.Channel;
import net.devh.springboot.autoconfigure.grpc.client.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class UsersGrpcClient {

    @GrpcClient(value = "nacos-grpc-demo")
    private Channel channel;

    public void userInfo(Long id){
        UsersGrpc.UsersBlockingStub usersBlockingStub = UsersGrpc.newBlockingStub(channel);
        UsersOuterClass.GetUserInfoRequest request = UsersOuterClass.GetUserInfoRequest.newBuilder().setId(1L).build();
        UsersOuterClass.GetUserInfoResponse response = usersBlockingStub.getUserInfo(request);
        System.out.println(response.toString());
    }
}
