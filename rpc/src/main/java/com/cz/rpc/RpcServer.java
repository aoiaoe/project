package com.cz.rpc;

import com.cz.rpc.client.IDemoService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * @author jzm
 * @date 2023/4/20 : 17:12
 */
public class RpcServer {

    public RpcServer(){
        ServiceLoader<IDemoService> load = ServiceLoader.load(IDemoService.class);
        Iterator<IDemoService> iterator = load.iterator();
        if(iterator.hasNext()){
            handlers.put(IDemoService.class.getName(), iterator.next());
        }
    }

    private Map<String, Object> handlers = new HashMap<>();

    public void export(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                // 获取到客户端请求新开一个线程进行处理
                Socket client = serverSocket.accept();
                new Thread(() -> {
                    ObjectInputStream oip = null;
                    ObjectOutputStream oos = null;
                    try {
                        oip = new ObjectInputStream(client.getInputStream());
                        String className = oip.readUTF();
                        String methodName = oip.readUTF();
                        Class[] paramType = (Class[])oip.readObject();
                        Object[] params = (Object[]) oip.readObject();
                        Class clz = Class.forName(className);
                        Object ins = handlers.get(className);
                        Method method = clz.getMethod(methodName, paramType);
                        Object res = method.invoke(ins, params);
                        oos = new ObjectOutputStream(client.getOutputStream());
                        oos.writeObject(res);
                    } catch (Exception e) {
                        try {
                            oos.writeObject(e);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    } finally {
                        if (oip != null) {
                            try {
                                oip.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (oos != null) {
                            try {
                                oos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addHandler(String claName, Object handler){
        handlers.put(claName, handler);
    }
}
