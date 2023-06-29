package lagou.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lagou.demo.codec.MessageDecoder;
import lagou.demo.codec.String2ByteBufEncoder;
import lagou.demo.handler.NettyServerHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        //  1.创建bossGroup线程组: 处理网络事件--连接事件
        EventLoopGroup boos = new NioEventLoopGroup(1);
        //  2.创建workerGroup线程组:处理网络事件-读写事件
        EventLoopGroup worker = new NioEventLoopGroup();
        //  3.创建服务端启动助手
        ServerBootstrap server = new ServerBootstrap();
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
                                .addLast(new MessageDecoder())
                                .addLast(new String2ByteBufEncoder())
                                .addLast(new NettyServerHandler());
                    }
                });
        //  9.启动服务端并绑定端口,同时将异步改为同步
        ChannelFuture future = server.bind(9999);
        future.addListener(f -> {
            if(f.isSuccess()){
                log.info("端口绑定完成");
            } else {
                log.info("端口绑定失败");
            }
        });

        //  10.关闭通道(并不是真正意义上的关闭，而是监听通道关闭的状态)和关闭连接池
        future.channel().closeFuture().sync();
        boos.shutdownGracefully();
        worker.shutdownGracefully();
    }
}
