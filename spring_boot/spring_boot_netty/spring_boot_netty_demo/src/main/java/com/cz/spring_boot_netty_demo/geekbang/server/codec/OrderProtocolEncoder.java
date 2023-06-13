package com.cz.spring_boot_netty_demo.geekbang.server.codec;

import com.cz.spring_boot_netty_demo.geekbang.common.ResponseMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class OrderProtocolEncoder extends MessageToMessageEncoder<ResponseMsg> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ResponseMsg msg, List<Object> out) throws Exception {
        final ByteBuf buffer = ctx.alloc().buffer();
        msg.encode(buffer);
        out.add(buffer);
    }
}
