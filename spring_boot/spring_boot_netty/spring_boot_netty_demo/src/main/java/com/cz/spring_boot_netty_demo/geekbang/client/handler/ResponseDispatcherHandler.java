package com.cz.spring_boot_netty_demo.geekbang.client.handler;

import com.cz.spring_boot_netty_demo.geekbang.client.dispatcher.RequestPendingCenter;
import com.cz.spring_boot_netty_demo.geekbang.common.ResponseMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ResponseDispatcherHandler extends SimpleChannelInboundHandler<ResponseMsg> {

    private RequestPendingCenter requestPendingCenter;

    public ResponseDispatcherHandler(RequestPendingCenter requestPendingCenter) {
        this.requestPendingCenter = requestPendingCenter;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ResponseMsg msg) throws Exception {
        requestPendingCenter.set(msg.getMsgHeader().getStreamId(), msg.getMsgBody());
    }
}
