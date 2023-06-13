package com.cz.spring_boot_netty_demo.geekbang.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class ServerIdleCheckHandler extends IdleStateHandler {
    public ServerIdleCheckHandler() {
        super(10, 0, 0, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        if(evt == IdleStateEvent.FIRST_READER_IDLE_STATE_EVENT){
            log.info("空闲检测，关闭连接");
            ctx.close();
            return;
        }
        super.channelIdle(ctx, evt);
    }
}
