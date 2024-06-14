package com.jzm.im.handler;

import com.jzm.common.bean.msg.ProtoMsg;
import com.jzm.common.executor.FutureTaskScheduler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@ChannelHandler.Sharable
public class HeartBeatHandler extends IdleStateHandler {

    private static final int READ_IDLE_GAP = 150;

    public HeartBeatHandler() {
        super(READ_IDLE_GAP, 0, 0, TimeUnit.SECONDS);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg == null ){
            super.channelRead(ctx, msg);
            return;
        }
        ProtoMsg.Message pkg = (ProtoMsg.Message) msg;
        if(pkg.getType() != ProtoMsg.HeadType.HEART_BEAT){
            super.channelRead(ctx, msg);
            return;
        }
        FutureTaskScheduler.add(() -> {
            if(ctx.channel().isActive()){
                ctx.channel().writeAndFlush(msg);
            }
        });
    }
}
