package com.cz.spring_boot_netty_demo.geekbang.server.handler;

import com.cz.spring_boot_netty_demo.geekbang.common.Operation;
import com.cz.spring_boot_netty_demo.geekbang.common.OperationResult;
import com.cz.spring_boot_netty_demo.geekbang.common.RequestMsg;
import com.cz.spring_boot_netty_demo.geekbang.common.ResponseMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderServerProcessHandler extends SimpleChannelInboundHandler<RequestMsg> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestMsg msg) throws Exception {
        final Operation msgBody = msg.getMsgBody();
        final OperationResult result = msgBody.execute();
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setMsgHeader(msg.getMsgHeader());
        responseMsg.setMsgBody(result);
        // 写 并发送
        log.info("写消息");
        ctx.writeAndFlush(responseMsg);
    }
}
