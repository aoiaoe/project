package com.jzm.im.handler;

import com.jzm.common.bean.msg.ProtoMsg;
import com.jzm.common.executor.FutureTaskScheduler;
import com.jzm.im.processor.ChatRedirectProcessor;
import com.jzm.im.session.ServerSession;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@ChannelHandler.Sharable
public class ChatRedirectHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private ChatRedirectProcessor chatRedirectProcessor;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg == null){
            super.channelRead(ctx, msg);
            return;
        }
        ProtoMsg.Message pkg = (ProtoMsg.Message) msg;
        if(pkg.getType() != ProtoMsg.HeadType.MESSAGE_REQUEST){
            super.channelRead(ctx, msg);
            return;
        }

        ServerSession session = ctx.channel().attr(ServerSession.SESSION_KEY).get();
        // 判断是否登录
        if(session == null || !session.isLogin()){
            log.error("用户尚未登录，不能发送消息");
            return;
        }

        FutureTaskScheduler.add(() -> {
            chatRedirectProcessor.process(session, pkg);
        });

    }
}
