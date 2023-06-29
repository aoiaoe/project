package lagou.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lagou.chat.handler.ChatClientHandler;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Scanner;

public class ChatClient {

    public static void main(String[] args) throws InterruptedException {
       new ChatClient().run("127.0.0.1", 9988);
    }

    private void run(String host, int port) throws InterruptedException {
        EventLoopGroup group = null;
        try {
            group = new NioEventLoopGroup();
            Bootstrap client = new Bootstrap();
            client.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new StringDecoder())
                                    .addLast(new StringEncoder())
                                    .addLast(new ChatClientHandler());

                        }
                    });

            ChannelFuture future = client.connect(new InetSocketAddress(host, port)).sync();
            Channel channel = future.channel();
            System.out.println("当前连接: " + channel.localAddress().toString().substring(1));
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()){
                ChannelFuture send = channel.writeAndFlush(scanner.nextLine());
                send.sync();
            }
            channel.closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }
}
