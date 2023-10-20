package com.cz.learnnetty.chatroom.code;

import com.cz.learnnetty.chatroom.message.Message;
import com.cz.learnnetty.chatroom.protocol.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

@Slf4j
public class MessageCodec extends ByteToMessageCodec<Message> {

    private Serializer<Message> serializer;
    public MessageCodec(Serializer serializer){
        this.serializer = serializer;
    }

    private final byte VERSION = 1;
    @Override
    public void encode(ChannelHandlerContext ctx, Message message, ByteBuf out) throws Exception {
        // 写入4字节魔数
        out.writeBytes(new byte[]{1,2,3,4});
        // 写入1字节版本
        out.writeByte(VERSION);
        // 写入1字节序列化方式 比如可以 0:jdk序列化 1:json序列化 3....
        out.writeByte(serializer.getType());
        // 写入1字节消息的类型
        out.writeByte(message.getMsgType());
        // 写入4字节顺序id
        out.writeInt(message.getSeqId());
        // 无意义，对齐填充
        out.writeByte(0xff);
        // 获取对象字节数组
        // 对象流
        byte[] bytes = serializer.serialize(message);
        // 长度
        out.writeInt(bytes.length);
        out.writeBytes(bytes);
    }

    @Override
    public void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int magicNumber = byteBuf.readInt();
        int version = byteBuf.readByte();
        int serialType = byteBuf.readByte();
        int msgType = byteBuf.readByte();
        int seqId = byteBuf.readInt();
        byteBuf.readByte();
        int length = byteBuf.readInt();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes, 0, length);

        Class<?> clazz = Message.getMessageClass(msgType);
        Object message = serializer.deserialize(clazz, bytes);
        log.info("解码数据:{}", message);
        list.add(message);
    }
}
