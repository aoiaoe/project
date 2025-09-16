package com.cz.spring_cloud_jdhotkey.adapter;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.options.WatchOption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class EtcdWatchService {
    @Autowired
    private Client etcdClient;

    @PostConstruct
    public void watchKey() {
        ByteSequence watchKey = ByteSequence.from("/k1".getBytes());
        
        etcdClient.getWatchClient().watch(watchKey, WatchOption.newBuilder().isPrefix(true).build(), response -> {
            response.getEvents().forEach(event -> {
                String key = event.getKeyValue().getKey().toString();
                String value = event.getKeyValue().getValue().toString();
                log.info("ETCD key changed - Key: {}, Value: {}, EventType: {}", 
                    key, value, event.getEventType());
            });
        });
    }
}
