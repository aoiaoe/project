package com.cz.learnnetty;

import com.cz.learnnetty.chatroom.code.MessageCodec;
import com.cz.learnnetty.chatroom.message.LoginRequestMessage;
import com.cz.learnnetty.chatroom.protocol.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class MessageCodecTest {

    @Test
    public void testCodec() throws Exception {
        MessageCodec messageCodec = new MessageCodec(Serializer.Algorithm.JDK);
        EmbeddedChannel channel = new EmbeddedChannel(new LoggingHandler(LogLevel.DEBUG), messageCodec);
        LoginRequestMessage loginRequestMessage = new LoginRequestMessage();
        loginRequestMessage.setUserName("jzm");
        loginRequestMessage.setPassword("123456");
        loginRequestMessage.setSeqId(1);
        loginRequestMessage.setMsgId(1);

        // 编码
//        channel.writeOutbound(loginRequestMessage);

        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        messageCodec.encode(null, loginRequestMessage, buffer);

        channel.writeInbound(buffer);
    }

    @Test
    public void testMap(){
        Map<String, Map<String, String>> map = new HashMap<>();
        Map<String, String> subMap = new HashMap();
        subMap.put("aa", "aa");
        map.put("2", new HashMap<>());
        System.out.println(subMap);
        System.out.println(map.computeIfAbsent("1", e -> subMap));
        System.out.println(map.computeIfAbsent("2", e -> subMap));
        System.out.println(map.computeIfAbsent("3", e -> subMap));
    }
}
