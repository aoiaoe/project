package com.jzm.im.chat;

import com.jzm.common.protocol.ChatProtobufCodec;
import com.jzm.common.protocol.ProtobufCodec;
import com.jzm.im.handler.ExceptionHandler;
import com.jzm.im.handler.LoginHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ChatServer {

    @Value("${chat.server.port:21000}")
    private int port;

    private NioEventLoopGroup bg;
    private NioEventLoopGroup wg;

    @Autowired
    private LoginHandler loginHandler;

    private ChatProtobufCodec chatProtobufCodec = new ChatProtobufCodec();
    @Autowired
    private ExceptionHandler exceptionHandler;


    private ServerBootstrap b = new ServerBootstrap();

    public void startChatServer() {
        bg = new NioEventLoopGroup(1);
        wg = new NioEventLoopGroup();

        try {
            b
                    .group(bg, wg)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(port)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast("chatProtobufCodec", chatProtobufCodec.clone())
                                    .addLast("logHandler", loginHandler)
                                    .addLast("exceptionHandler", exceptionHandler);
                        }
                    });

            ChannelFuture future = b.bind().sync();
            log.info("ChatServer 启动成功, 端口:{}", future.channel().localAddress());

            // 监听通道关闭事件
            ChannelFuture channelFuture = future.channel().closeFuture().sync();

        } catch (Exception e) {
            throw new IllegalStateException("Start chat server error!", e);
        } finally {
            bg.shutdownGracefully();
            wg.shutdownGracefully();
        }
    }
}
