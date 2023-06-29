package com.cz.netty_rpc.common;

import lombok.Data;

@Data
public class RpcResponse {

    private String requestId;

    private String error;

    private Object result;
}
