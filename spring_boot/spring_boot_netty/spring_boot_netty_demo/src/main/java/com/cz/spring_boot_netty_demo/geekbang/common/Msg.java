package com.cz.spring_boot_netty_demo.geekbang.common;

import com.cz.spring_boot_netty_demo.geekbang.util.JsonUtil;
import io.netty.buffer.ByteBuf;
import lombok.Data;

import java.nio.charset.Charset;

@Data
public abstract class Msg<T extends MsgBody> {

    private MsgHeader msgHeader;
    private T msgBody;

    public T getMsgBody(){
        return msgBody;
    }

    public void encode(ByteBuf byteBuf){
        byteBuf.writeInt(msgHeader.getVersion());
        byteBuf.writeLong(msgHeader.getStreamId());
        byteBuf.writeInt(msgHeader.getOpCode());
        byteBuf.writeBytes(JsonUtil.toJson(msgBody).getBytes());
    }

    public abstract Class<T> getMsgBodyDecodeClass(int opCode);

    public void decode(ByteBuf msg){
        int version = msg.readInt();
        long streamId = msg.readLong();
        int opCode = msg.readInt();

        MsgHeader header = new MsgHeader();
        header.setVersion(version);
        header.setStreamId(streamId);
        header.setOpCode(opCode);

        this.msgHeader = header;

        Class<T> bodyClazz = getMsgBodyDecodeClass(opCode);
        msgBody = JsonUtil.parse(msg.toString(Charset.forName("UTF-8")),bodyClazz);
    }
}
