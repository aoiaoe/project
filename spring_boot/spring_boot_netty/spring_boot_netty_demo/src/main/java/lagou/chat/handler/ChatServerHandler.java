package lagou.chat.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

@ChannelHandler.Sharable
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    private List<Channel> channelSet = new ArrayList<>();
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelSet.add(channel);
        System.out.println("---客户端:" + channel.remoteAddress().toString().substring(1) + "上线了" + " 缓存长度:" + channelSet.size());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        Channel channel = ctx.channel();
        for (Channel ch : channelSet) {
            if(channel == ch){
                continue;
            }
            ch.writeAndFlush("[客户端]" + channel.remoteAddress().toString().substring(1) + "说:" + s);

        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("!!!客户端:" + channel.remoteAddress().toString().substring(1) + "下线了");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        Channel channel = ctx.channel();
        System.out.println("!!!客户端:" + channel.remoteAddress().toString().substring(1) + "下线了");
    }
}
