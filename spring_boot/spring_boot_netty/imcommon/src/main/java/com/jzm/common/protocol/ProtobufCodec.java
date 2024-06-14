package com.jzm.common.protocol;

import com.jzm.common.bean.msg.ProtoMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

public class ProtobufCodec extends MessageToMessageCodec<ByteBuf, ProtoMsg.Message> {


    @Override
    protected void encode(ChannelHandlerContext ctx, ProtoMsg.Message msg, List<Object> out) throws Exception {
        if (msg == null){
            return;
        }
        byte[] bytes = msg.toByteArray();
        ByteBuf buffer = ctx.alloc().buffer(bytes.length);
        buffer.writeBytes(bytes);
        out.add(buffer);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        byte[] bytes = null;
        // 堆内
        if (msg.hasArray()) {
            ByteBuf slice = msg.slice(0, msg.readableBytes());
            bytes = slice.array();
        } else { // 堆外
            bytes = new byte[msg.readableBytes()];
            msg.readBytes(bytes);
        }
        ProtoMsg.Message message = ProtoMsg.Message.parseFrom(bytes);
        if (message != null) {
            out.add(message);
        }
    }
}
