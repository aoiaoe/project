package com.jzm.im.handler;

import com.jzm.im.session.ServerSession;
import exception.InvalidFrameException;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@ChannelHandler.Sharable
@Service
@Slf4j
public class ExceptionHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if(cause instanceof InvalidFrameException){
            cause.printStackTrace();
            log.error(cause.getMessage());
        }

        if (cause instanceof IOException) {
            //远程连接，已经关闭
            log.error(cause.getMessage());
            log.error("客户端已经关闭连接，这里需要做下线处理");
            ServerSession.closeSession(ctx);
        } else {
            cause.printStackTrace();

            //捕捉异常信息
//            cause.printStackTrace();
            log.error(cause.getMessage());
//            ctx.close();
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ServerSession.closeSession(ctx);
    }
}
