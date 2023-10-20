package com.cz.learnnetty.chatroom.protocol;

import com.alibaba.fastjson.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public interface Serializer<T> {

    <T> T deserialize(Class<T> clazz, byte[] bytes);

    <T> byte[] serialize(T obj);

    byte getType();

    enum Algorithm implements Serializer{
        JDK{
            @Override
            public byte getType() {
                return 0;
            }

            @Override
            public byte[] serialize(Object obj) {
                try {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(bos);
                    oos.writeObject(obj);
                    return  bos.toByteArray();
                }catch (Exception e){
                    throw new RuntimeException("序列化失败", e);
                }
            }

            @Override
            public Object deserialize(Class clazz, byte[] bytes) {
                try {
                    ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                    ObjectInputStream ois = new ObjectInputStream(bis);
                    return clazz.cast(ois.readObject());
                }catch (Exception e){
                    throw new RuntimeException("反序列化失败", e);
                }
            }
        },
        JSON{
            @Override
            public byte getType() {
                return 1;
            }

            @Override
            public byte[] serialize(Object obj) {
                return JSONObject.toJSONString(obj).getBytes(Charset.forName("UTF-8"));
            }

            @Override
            public Object deserialize(Class clazz, byte[] bytes) {
                return JSONObject.parseObject(new String(bytes, Charset.forName("UTF-8")), clazz);
            }
        }
    }
}
