package com.cz.spring_boot_netty_demo.geekbang.client.handler;

import com.cz.spring_boot_netty_demo.geekbang.common.RequestMsg;
import com.cz.spring_boot_netty_demo.geekbang.common.keepalive.KeepAliveOperation;
import com.cz.spring_boot_netty_demo.geekbang.util.IdUtil;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KeepAliveHandler extends ChannelDuplexHandler {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt == IdleStateEvent.FIRST_WRITER_IDLE_STATE_EVENT){
            log.info("写空闲检测到, 发送保活消息");
            KeepAliveOperation keepAliveOperation = new KeepAliveOperation();
            RequestMsg msg = new RequestMsg(IdUtil.nextId(), keepAliveOperation);
            ctx.writeAndFlush(msg);
        }
        super.userEventTriggered(ctx, evt);
    }
}
