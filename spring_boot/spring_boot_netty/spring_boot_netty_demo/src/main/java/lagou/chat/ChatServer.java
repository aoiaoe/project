package lagou.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lagou.chat.handler.ChatServerHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatServer {

    public static void main(String[] args) throws InterruptedException {
        new ChatServer().run(9988);
    }

    public void run(final int port) throws InterruptedException {
            EventLoopGroup boos = null;
            EventLoopGroup worker = null;
        try {
            //  1.创建bossGroup线程组: 处理网络事件--连接事件
            boos = new NioEventLoopGroup(1);
            //  2.创建workerGroup线程组:处理网络事件-读写事件
            worker = new NioEventLoopGroup();
            //  3.创建服务端启动助手
            ServerBootstrap server = new ServerBootstrap();

            // 共享的handler，需要声明在外面
            ChatServerHandler chatServerHandler = new ChatServerHandler();
            //  4. 设置bossGroup线程组和workerGroup线程组
            server.group(boos, worker)
                    //  5.设置服务端通道实现为NIO
                    .channel(NioServerSocketChannel.class)
                    //  6.参数设置
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)
                    //  7.创建一个通道初始化对象
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            //  8.向pipeline中添加自定义业务处理handler
                            channel.pipeline()
                                    .addLast(new StringEncoder())
                                    .addLast(new StringDecoder())
                                    .addLast(chatServerHandler);
//                                    .addLast(new MessageDecoder())
//                                    .addLast(new String2ByteBufEncoder())
//                                    .addLast(new NettyServerHandler());
                        }
                    });
            //  9.启动服务端并绑定端口,同时将异步改为同步
            ChannelFuture future = server.bind(port);
            future.addListener(f -> {
                if (f.isSuccess()) {
                    log.info("端口绑定完成");
                } else {
                    log.info("端口绑定失败");
                }
            });

            //  10.关闭通道(并不是真正意义上的关闭，而是监听通道关闭的状态)和关闭连接池
            future.channel().closeFuture().sync();
        } finally {
            boos.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
