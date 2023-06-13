package com.cz.spring_boot_netty_demo.geekbang.server.codec;

import com.cz.spring_boot_netty_demo.geekbang.common.RequestMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class OrderProtocolDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        RequestMsg msg = new RequestMsg();
        msg.decode(byteBuf);

        out.add(msg);
    }
}
