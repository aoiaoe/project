package com.cz.netty_rpc.common;

import io.netty.buffer.ByteBuf;
import lombok.Data;

import java.nio.charset.StandardCharsets;

@Data
public class RpcRequest {

    private String requestId;
    private String className;
    private String methodName;
    // 入参类型
    private Class<?>[] parameterTypes;
    // 入参
    private Object[] parameters;

}
