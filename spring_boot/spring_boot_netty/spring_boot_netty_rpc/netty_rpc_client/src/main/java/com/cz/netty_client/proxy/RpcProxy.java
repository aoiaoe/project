package com.cz.netty_client.proxy;

import com.alibaba.fastjson.JSONObject;
import com.cz.netty_client.client.RpcClient;
import com.cz.netty_rpc.common.RpcRequest;
import com.cz.netty_rpc.common.RpcResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * 客户端代理对象
 * 1、封装request请求对象
 * 2、创建RpcClient对象
 * 3、发送消息
 * 4、返回结果
 */
public class RpcProxy {

    public static Object createProxy(Class serviceClass) {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{serviceClass}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        RpcRequest request = new RpcRequest();
                        request.setRequestId(UUID.randomUUID().toString());
                        request.setClassName(serviceClass.getName());
                        request.setMethodName(method.getName());
                        request.setParameters(args);
                        request.setParameterTypes(method.getParameterTypes());

                        RpcClient client = new RpcClient("127.0.0.1", 8899);
                        try {
                            Object response = client.send(JSONObject.toJSONString(request));
                            RpcResponse rpcResponse = JSONObject.parseObject(response.toString(), RpcResponse.class);
                            if (rpcResponse.getError() != null) {
                                throw new RuntimeException(rpcResponse.getError());
                            }
                            return JSONObject.parseObject(rpcResponse.getResult().toString(), method.getReturnType());
                        }catch (Exception e){
                            throw e;
                        } finally {
                            client.close();
                        }
                    }
                });
    }
}
