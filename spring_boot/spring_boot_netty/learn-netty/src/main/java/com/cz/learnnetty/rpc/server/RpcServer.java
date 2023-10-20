package com.cz.learnnetty.rpc.server;

import com.cz.learnnetty.chatroom.code.MessageCodec;
import com.cz.learnnetty.chatroom.protocol.ProtocolFrameDecoder;
import com.cz.learnnetty.chatroom.protocol.Serializer;
import com.cz.learnnetty.rpc.server.handler.RpcHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class RpcServer {

    public static void main(String[] args) {
        NioEventLoopGroup boss = null;
        NioEventLoopGroup worker = null;

        try {
            boss = new NioEventLoopGroup(1);
            worker = new NioEventLoopGroup();
            LoggingHandler loggingHandler = new LoggingHandler(LogLevel.INFO);
            RpcHandler rpcHandler = new RpcHandler();
            ChannelFuture channelFuture = new ServerBootstrap()
                    .group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new ProtocolFrameDecoder())
                                    .addLast(new MessageCodec(Serializer.Algorithm.JSON))
                                    .addLast(loggingHandler)
                                    // rpc处理器
                                    .addLast(rpcHandler);
                        }
                    }).bind(8080).sync();
            Channel channel = channelFuture.channel();

            channel.closeFuture().sync();

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
