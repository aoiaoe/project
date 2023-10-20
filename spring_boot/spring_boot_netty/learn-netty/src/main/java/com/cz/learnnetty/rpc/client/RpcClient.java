package com.cz.learnnetty.rpc.client;

import com.cz.learnnetty.chatroom.code.MessageCodec;
import com.cz.learnnetty.chatroom.protocol.ProtocolFrameDecoder;
import com.cz.learnnetty.chatroom.protocol.Serializer;
import com.cz.learnnetty.rpc.client.handler.RpcResponseHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;

public class RpcClient {

    public static void main(String[] args) {
        NioEventLoopGroup worker = null;

        try {
            worker = new NioEventLoopGroup();
            LoggingHandler loggingHandler = new LoggingHandler();
            RpcResponseHandler handler = new RpcResponseHandler();
            ChannelFuture channelFuture = new Bootstrap()
                    .group(worker)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new ProtocolFrameDecoder())
                                    .addLast(new MessageCodec(Serializer.Algorithm.JSON))
                                    .addLast(loggingHandler)
                                    .addLast(handler);

                        }
                    }).connect(new InetSocketAddress("localhost", 8080)).sync();
            Channel channel = channelFuture.channel();
            channel.closeFuture().sync();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
        }
    }
}
