package com.cz.rpc.client;

import com.alibaba.fastjson.JSON;
import com.cz.rpc.protolcol.RpcProtocol;
import com.cz.rpc.server.RpcInvocation;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

import static com.cz.rpc.common.Constants.RESP_MAP;

/**
 * @Author linhao
 * @Date created in 8:21 下午 2021/11/24
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //客户端和服务端之间的数据都是以RpcProtocol对象作为基本协议进行的交互
        RpcProtocol rpcProtocol = (RpcProtocol) msg;
        //这里是传输参数更为详细的RpcInvocation对象字节数组。
        byte[] reqContent = rpcProtocol.getContent();
        String json = new String(reqContent,0,reqContent.length);
        RpcInvocation rpcInvocation = JSON.parseObject(json,RpcInvocation.class);
        //通过之前发送的uuid来注入匹配的响应数值
        if(!RESP_MAP.containsKey(rpcInvocation.getUuid())){
            throw new IllegalArgumentException("server response is error!");
        }
        //将请求的响应结构放入一个Map集合中，集合的key就是uuid，这个uuid在发送请求之前就已经初始化好了，所以只需要起一个线程在后台遍历这个map，查看对应的key是否有相应即可。
        //uuid何时放入到map？其实放入的操作我将它封装到了代理类中进行实现，这块请看下边讲解。
        RESP_MAP.put(rpcInvocation.getUuid(),rpcInvocation);
        ReferenceCountUtil.release(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        Channel channel = ctx.channel();
        if(channel.isActive()){
            ctx.close();
        }
    }
}
