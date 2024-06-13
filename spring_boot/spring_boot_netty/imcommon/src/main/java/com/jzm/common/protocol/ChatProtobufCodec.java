package com.jzm.common.protocol;


import com.jzm.common.bean.msg.ProtoMsg;
import com.jzm.common.constant.ProtocolConstants;
import exception.InvalidFrameException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Slf4j
public class ChatProtobufCodec extends ByteToMessageCodec<ProtoMsg.Message> implements Cloneable{

    /**
     * 消息体格式:
     *  固定长度 + 变长<br/>
     *  固定长度 = 魔数 + 版本 + 消息体长度<br/>
     *             2  +  2  +   4
     *
     * @param ctx
     * @param msg
     * @param buf
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext ctx,
                          ProtoMsg.Message msg, ByteBuf buf) throws Exception {
        buf.writeShort(ProtocolConstants.MAGIC_NUM);
        buf.writeShort(ProtocolConstants.VERSION);
        byte[] bytes = msg.toByteArray();
        // 消息长度
        buf.writeInt(bytes.length);
        // 消息体
        buf.writeBytes(bytes);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx,
                          ByteBuf in, List<Object> list) throws Exception {
        in.markReaderIndex();
        // 判断是否达到 len(魔数 + 版本 + 消息体长度) = 8字节固定长度
        if(in.readableBytes() < 8){
            return;
        }
        short magicNum = in.readShort();
        if(magicNum != ProtocolConstants.MAGIC_NUM){
            String error = "客户端口令不对:" + ctx.channel().remoteAddress();
            //异常连接，直接报错，关闭连接
            throw new InvalidFrameException(error);
        }
        short version = in.readShort();
        if(version > ProtocolConstants.VERSION || version < ProtocolConstants.VERSION_MIN){
            String error = "协议的版本不对:" + ctx.channel().remoteAddress();
            //异常连接，直接报错，关闭连接
            throw new InvalidFrameException(error);
        }
        int length = in.readInt();
        if(length < 0){
            // 非正数长度，考虑非法攻击客户端，关闭链接
            ctx.close();
        }
        // 数据还没有达到数据包长度
        if(in.readableBytes() < length){
            // 重置读取位置
            in.resetReaderIndex();
            return;
        }
        byte[] bytes = new byte[length];
        in.readBytes(bytes);
        try {
            ProtoMsg.Message message = ProtoMsg.Message.parseFrom(bytes);
            Optional.ofNullable(message).ifPresent(e -> list.add(e));
        }catch (Exception e){
            log.error("反序列化数据包出错:", e);
        }
    }

    public ChatProtobufCodec clone() {
        try {
            return (ChatProtobufCodec)super.clone();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
