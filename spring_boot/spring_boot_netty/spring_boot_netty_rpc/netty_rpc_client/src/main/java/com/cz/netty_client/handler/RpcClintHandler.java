package com.cz.netty_client.handler;

import com.cz.netty_rpc.common.RpcRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.Data;
import lombok.Setter;

import java.util.concurrent.Callable;

/**
 * 客户端
 * 1、发送消息
 * 2、接受消息
 */
@Data
public class RpcClintHandler extends SimpleChannelInboundHandler<String> implements Callable {

    private ChannelHandlerContext ctx;
    private String request;
    private String response;
    /**
     * 通道连接就绪事件
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
    }

    @Override
    protected synchronized void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        response = s;
        // 唤醒等待的线程

        notify();
    }

    /**
     * 发送消息到服务端
     * @return
     * @throws Exception
     */
    @Override
    public synchronized Object call() throws Exception {
        ctx.writeAndFlush(request);
        // 线程等待
        wait();
        return response;
    }
}
