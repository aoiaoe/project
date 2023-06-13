package com.cz.spring_boot_netty_demo.geekbang.server.handler;

import com.cz.spring_boot_netty_demo.geekbang.common.Operation;
import com.cz.spring_boot_netty_demo.geekbang.common.OperationResult;
import com.cz.spring_boot_netty_demo.geekbang.common.RequestMsg;
import com.cz.spring_boot_netty_demo.geekbang.common.auth.AuthOperation;
import com.cz.spring_boot_netty_demo.geekbang.common.auth.AuthOperationResult;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@ChannelHandler.Sharable
@Slf4j
public class AuthHandler extends SimpleChannelInboundHandler<RequestMsg> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestMsg msg) throws Exception {
        try {
            final Operation operation = msg.getMsgBody();
            if(operation instanceof AuthOperation){
                final AuthOperation authOperation = AuthOperation.class.cast(operation);
                final AuthOperationResult authResult = authOperation.execute();
                if(authResult.isPassAuth()){
                    log.info("授权成功");
                } else {
                    log.info("授权失败");
                    ctx.close();
                }
            } else {
                log.info("授权失败");
                ctx.close();
            }
        }catch (Exception e){
            log.info("异常，授权失败");
            ctx.close();
        }finally {
            // 如果授权成功，则将handler从pipeline中移除
            ctx.pipeline().remove(this);
        }

    }
}
