package com.cz.netty_rpc_server.serivce;

import com.alibaba.fastjson.JSONObject;
import com.cz.netty_rpc.common.RpcRequest;
import com.cz.netty_rpc.common.RpcResponse;
import com.cz.netty_rpc_server.anno.RpcService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.BeansException;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * 1、将有@RpcService注解的bean进行缓存
 * 2、接受请求
 * 3、根据传过来的beanName从缓存中查找对应的bean
 * 4、解析请求中的方法名称，参数类型，参数
 * 5、通过反射调用bean的方法
 * 6、给客户端进行响应
 */
@ChannelHandler.Sharable
@Component
public class RpcServerHandler extends SimpleChannelInboundHandler<String> implements ApplicationContextAware {

    private static final Map<String, Object> SERVICE_BEANS = new HashMap<>();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        RpcRequest request = JSONObject.parseObject(s, RpcRequest.class);
        RpcResponse response = new RpcResponse();
        response.setRequestId(request.getRequestId());
        try{
            handler(request, response);
        } catch (Exception e){
            e.printStackTrace();
            response.setError(e.getMessage());
        }
        channelHandlerContext.writeAndFlush(JSONObject.toJSONString(response));
    }

    private void handler(RpcRequest request, RpcResponse response) throws InvocationTargetException {
        Object service = SERVICE_BEANS.get(request.getClassName());
        if(service == null){
            response.setError("处理类不存在");
            return;
        }
        Class<?> serviceClass = service.getClass();
        FastClass fastClass = FastClass.create(serviceClass);
        FastMethod method = fastClass.getMethod(request.getMethodName(), request.getParameterTypes());
        Object result = method.invoke(service, request.getParameters());

        response.setResult(result);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> serviceMaps = applicationContext.getBeansWithAnnotation(RpcService.class);
        if(serviceMaps != null && serviceMaps.size() > 0){
            for (Map.Entry<String, Object> entry : serviceMaps.entrySet()) {
                Object service = entry.getValue();
                Class<?>[] interfaces = service.getClass().getInterfaces();
                if(interfaces.length<1){
                    throw new RuntimeException("@RpcService注解的类必须实现接口");
                }
                // 默认取第一个接口为缓存bean的名称
                SERVICE_BEANS.put(interfaces[0].getName(), service);
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("【客户端】:" + ctx.channel().remoteAddress().toString().substring(1) + "上线");
    }
}
