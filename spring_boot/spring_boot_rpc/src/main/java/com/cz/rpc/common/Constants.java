package com.cz.rpc.common;

import com.cz.rpc.server.RpcInvocation;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

public class Constants {

    public static final short MAGIC_NUMBER = 1;

    public static final Map<String, Object> PROVIDER_CLASS_MAP = new ConcurrentHashMap<>();

    public static final Map<String, Object> RESP_MAP = new ConcurrentHashMap<>(1000);
    public static final BlockingQueue<RpcInvocation> SEND_QUEUE = new LinkedBlockingDeque<>(1000);
}
