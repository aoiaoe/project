package com.cz.spring_cloud_jdhotkey.adapter;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.kv.PutResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/etcd")
public class EtcdController {

    @Autowired
    private Client etcdClient;

    @PostMapping(value = "/put")
    public boolean put(String key, String value) {
        CompletableFuture<PutResponse> put = etcdClient.getKVClient().put(ByteSequence.from(key.getBytes()), ByteSequence.from(value.getBytes()))
                .exceptionally(throwable -> {
                    throwable.printStackTrace();
                    throw new RuntimeException(throwable);
                });
        return put.isDone();
    }
}
