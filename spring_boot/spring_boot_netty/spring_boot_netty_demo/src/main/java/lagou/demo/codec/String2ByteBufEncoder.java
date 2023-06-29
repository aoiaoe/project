package lagou.demo.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class String2ByteBufEncoder extends MessageToMessageEncoder<String> {


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, String s, List<Object> list) throws Exception {
        System.out.println("消息编码....");
        ByteBuf byteBuf = Unpooled.copiedBuffer(s.getBytes(StandardCharsets.UTF_8));
        list.add(byteBuf);
    }
}
