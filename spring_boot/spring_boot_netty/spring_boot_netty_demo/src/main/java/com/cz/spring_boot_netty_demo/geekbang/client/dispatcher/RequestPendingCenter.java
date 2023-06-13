package com.cz.spring_boot_netty_demo.geekbang.client.dispatcher;

import com.cz.spring_boot_netty_demo.geekbang.common.OperationResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RequestPendingCenter {

    private Map<Long, OperationResultFuture> map = new ConcurrentHashMap<>();

    public void add(Long streamId, OperationResultFuture future){
        map.put(streamId, future);
    }

    public void set(Long streamId, OperationResult result){
        final OperationResultFuture future = this.map.get(streamId);
        if(future != null){
            future.setSuccess(result);
            map.remove(streamId);
        }
    }
}
