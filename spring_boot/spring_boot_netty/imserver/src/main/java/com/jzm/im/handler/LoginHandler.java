package com.jzm.im.handler;

import com.jzm.common.bean.msg.ProtoMsg;
import com.jzm.common.executor.CallableTask;
import com.jzm.common.executor.CallbackTaskScheduler;
import com.jzm.im.processor.LoginProcessor;
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
public class LoginHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private LoginProcessor loginProcessor;
    @Autowired
    private HeartBeatHandler heartBeatHandler;
    @Autowired
    private ChatRedirectHandler chatRedirectHandler;


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("收到一个新的连接，但是没有登录 {}", ctx.channel().id());
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg == null){
            super.channelRead(ctx, msg);
            return;
        }

        ProtoMsg.Message pkg = (ProtoMsg.Message) msg;

        //取得请求类型
        ProtoMsg.HeadType headType = pkg.getType();
        if(headType != ProtoMsg.HeadType.LOGIN_REQUEST){
            super.channelRead(ctx, msg);
            return;
        }

        // 创建session
        ServerSession session = new ServerSession(ctx.channel());

        CallbackTaskScheduler.add(new CallableTask<Boolean>() {
            @Override
            public Boolean execute() {
                boolean r = loginProcessor.process(session, pkg);
                return r;
            }

            @Override
            public void success(Boolean r) {
                if(r) {
                    // 登录成功, 添加后续处理器
                    ctx.channel().pipeline()
                            .addLast("heartBeat", heartBeatHandler)
                            .addLast("chatHandler", chatRedirectHandler);
                    ctx.pipeline().remove("logHandler");
                    log.info("登录成功:" + session.getUser());
                }else {
                    ServerSession.closeSession(ctx);
                    log.info("登录失败1:" + session.getUser());
                }
            }

            @Override
            public void fail(Throwable throwable) {
                ServerSession.closeSession(ctx);
                log.info("登录失败2:" + session.getUser());
            }
        });


    }
}
