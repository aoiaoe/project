package com.cz.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.14.0)",
    comments = "Source: Users.proto")
public final class UsersGrpc {

  private UsersGrpc() {}

  public static final String SERVICE_NAME = "Users";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.cz.grpc.UsersOuterClass.GetUserInfoRequest,
      com.cz.grpc.UsersOuterClass.GetUserInfoResponse> getGetUserInfoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetUserInfo",
      requestType = com.cz.grpc.UsersOuterClass.GetUserInfoRequest.class,
      responseType = com.cz.grpc.UsersOuterClass.GetUserInfoResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.cz.grpc.UsersOuterClass.GetUserInfoRequest,
      com.cz.grpc.UsersOuterClass.GetUserInfoResponse> getGetUserInfoMethod() {
    io.grpc.MethodDescriptor<com.cz.grpc.UsersOuterClass.GetUserInfoRequest, com.cz.grpc.UsersOuterClass.GetUserInfoResponse> getGetUserInfoMethod;
    if ((getGetUserInfoMethod = UsersGrpc.getGetUserInfoMethod) == null) {
      synchronized (UsersGrpc.class) {
        if ((getGetUserInfoMethod = UsersGrpc.getGetUserInfoMethod) == null) {
          UsersGrpc.getGetUserInfoMethod = getGetUserInfoMethod = 
              io.grpc.MethodDescriptor.<com.cz.grpc.UsersOuterClass.GetUserInfoRequest, com.cz.grpc.UsersOuterClass.GetUserInfoResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "Users", "GetUserInfo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cz.grpc.UsersOuterClass.GetUserInfoRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cz.grpc.UsersOuterClass.GetUserInfoResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new UsersMethodDescriptorSupplier("GetUserInfo"))
                  .build();
          }
        }
     }
     return getGetUserInfoMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static UsersStub newStub(io.grpc.Channel channel) {
    return new UsersStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static UsersBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new UsersBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static UsersFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new UsersFutureStub(channel);
  }

  /**
   */
  public static abstract class UsersImplBase implements io.grpc.BindableService {

    /**
     */
    public void getUserInfo(com.cz.grpc.UsersOuterClass.GetUserInfoRequest request,
        io.grpc.stub.StreamObserver<com.cz.grpc.UsersOuterClass.GetUserInfoResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetUserInfoMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetUserInfoMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.cz.grpc.UsersOuterClass.GetUserInfoRequest,
                com.cz.grpc.UsersOuterClass.GetUserInfoResponse>(
                  this, METHODID_GET_USER_INFO)))
          .build();
    }
  }

  /**
   */
  public static final class UsersStub extends io.grpc.stub.AbstractStub<UsersStub> {
    private UsersStub(io.grpc.Channel channel) {
      super(channel);
    }

    private UsersStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UsersStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new UsersStub(channel, callOptions);
    }

    /**
     */
    public void getUserInfo(com.cz.grpc.UsersOuterClass.GetUserInfoRequest request,
        io.grpc.stub.StreamObserver<com.cz.grpc.UsersOuterClass.GetUserInfoResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetUserInfoMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class UsersBlockingStub extends io.grpc.stub.AbstractStub<UsersBlockingStub> {
    private UsersBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private UsersBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UsersBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new UsersBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.cz.grpc.UsersOuterClass.GetUserInfoResponse getUserInfo(com.cz.grpc.UsersOuterClass.GetUserInfoRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetUserInfoMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class UsersFutureStub extends io.grpc.stub.AbstractStub<UsersFutureStub> {
    private UsersFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private UsersFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UsersFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new UsersFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.cz.grpc.UsersOuterClass.GetUserInfoResponse> getUserInfo(
        com.cz.grpc.UsersOuterClass.GetUserInfoRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetUserInfoMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_USER_INFO = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final UsersImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(UsersImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_USER_INFO:
          serviceImpl.getUserInfo((com.cz.grpc.UsersOuterClass.GetUserInfoRequest) request,
              (io.grpc.stub.StreamObserver<com.cz.grpc.UsersOuterClass.GetUserInfoResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class UsersBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    UsersBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.cz.grpc.UsersOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Users");
    }
  }

  private static final class UsersFileDescriptorSupplier
      extends UsersBaseDescriptorSupplier {
    UsersFileDescriptorSupplier() {}
  }

  private static final class UsersMethodDescriptorSupplier
      extends UsersBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    UsersMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (UsersGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new UsersFileDescriptorSupplier())
              .addMethod(getGetUserInfoMethod())
              .build();
        }
      }
    }
    return result;
  }
}
