package lagou.demo.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 消息解码器
 */
public class MessageDecoder extends MessageToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Object o, List list) throws Exception {
        System.out.println("正在进行解码");
        ByteBuf msg = (ByteBuf) o;
        list.add(msg.toString(StandardCharsets.UTF_8)); // 传递数据到下一个handler
    }
}
