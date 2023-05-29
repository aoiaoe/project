package com.cz.spring_boot_zookeeper.lock;

import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;

import java.io.UnsupportedEncodingException;

/**
 * 基于String的序列化器
 * @author jzm
 * @date 2023/5/18 : 11:08
 */
public class MyZkSerializer implements ZkSerializer {


    public Object deserialize(final byte[] bytes) throws ZkMarshallingError {
        try {
            return new String(bytes, "utf-8");
        } catch (final UnsupportedEncodingException e) {
            throw new ZkMarshallingError(e);
        }
    }

    public byte[] serialize(final Object data) throws ZkMarshallingError {
        try {
            return ((String) data).getBytes("utf-8");
        } catch (final UnsupportedEncodingException e) {
            throw new ZkMarshallingError(e);
        }
    }
}
