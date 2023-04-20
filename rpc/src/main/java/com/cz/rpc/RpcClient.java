package com.cz.rpc;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

/**
 * @author jzm
 * @date 2023/4/20 : 17:12
 */
public class RpcClient {

    public <T> T proxy(Class<T> clazz, String host, int port){
        return (T)Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                ObjectInputStream oip = null;
                ObjectOutputStream oos = null;
                try {
                    Socket socket = new Socket(host, port);
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    // 需要操作的方法名、方法参数类型、方法参数
                    oos.writeUTF(clazz.getName());
                    oos.writeUTF(method.getName());
                    oos.writeObject(method.getParameterTypes());
                    oos.writeObject(args);
                    // 获取返回结果
                    try {
                        oip = new ObjectInputStream(socket.getInputStream());
                        Object o = oip.readObject();
                        if(o instanceof Throwable){
                            throw (Throwable)o;
                        }
                        return o;
                    } catch (Exception e){
                        throw e;
                    }
                } catch (IOException e) {
                    throw e;
                } finally {
                    if(oos != null){
                        oos.close();
                    }
                    if(oip != null){
                        oip.close();
                    }
                }
            }
        });
    }
}
