package com.cz.spring_boot_netty_demo.geekbang.client;

import com.cz.spring_boot_netty_demo.geekbang.client.codec.*;
import com.cz.spring_boot_netty_demo.geekbang.client.dispatcher.OperationResultFuture;
import com.cz.spring_boot_netty_demo.geekbang.client.dispatcher.RequestPendingCenter;
import com.cz.spring_boot_netty_demo.geekbang.client.handler.ClientIdleCheckHandler;
import com.cz.spring_boot_netty_demo.geekbang.client.handler.KeepAliveHandler;
import com.cz.spring_boot_netty_demo.geekbang.client.handler.ResponseDispatcherHandler;
import com.cz.spring_boot_netty_demo.geekbang.common.OperationResult;
import com.cz.spring_boot_netty_demo.geekbang.common.RequestMsg;
import com.cz.spring_boot_netty_demo.geekbang.common.auth.AuthOperation;
import com.cz.spring_boot_netty_demo.geekbang.common.order.OrderOperation;
import com.cz.spring_boot_netty_demo.geekbang.util.IdUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ipfilter.IpFilterRule;
import io.netty.handler.ipfilter.IpFilterRuleType;
import io.netty.handler.ipfilter.IpSubnetFilterRule;
import io.netty.handler.ipfilter.RuleBasedIpFilter;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;

import javax.net.ssl.SSLException;
import java.util.concurrent.ExecutionException;

/**
 * 带响应分发器的netty客户端
 */
public class ClientV2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException, SSLException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.group(new NioEventLoopGroup());

        RequestPendingCenter pendingCenter = new RequestPendingCenter();
        KeepAliveHandler keepAliveHandler = new KeepAliveHandler();

        // ssl
        final SslContext sslContext = SslContextBuilder.forClient().build();


        bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ch.pipeline()
                        // pipeline是一个调用链，有来有回
                        // 所以handler必须放最后一个

                        // 写空闲检测处理区
                        .addLast(new ClientIdleCheckHandler())
                        // ssl
                        .addLast(sslContext.newHandler(ch.alloc()))
                        .addLast(new OrderFrameDecode())
                        .addLast(new OrderFrameEncoder())
                        .addLast(new OrderProtocolDecoder())
                        .addLast(new OrderProtocolEncoder())
                        // 此encoder将operation对象转化为RequestMsg对象
                        .addLast(new Operation2ReqMsgEncoder())
                        // 保活处理器
                        .addLast(keepAliveHandler)
                        // 响应分发器
                        .addLast(new ResponseDispatcherHandler(pendingCenter))
                        .addLast(new LoggingHandler(LogLevel.INFO));

            }
        });
 
        final ChannelFuture channelFuture = bootstrap.connect("127.0.0.1",8090);
        Long streamId = IdUtil.nextId();
        RequestMsg msg = new RequestMsg(streamId, new OrderOperation(101, "番茄牛腩"));
        RequestMsg auth = new RequestMsg(streamId, new AuthOperation("admin", "admin"));

        OperationResultFuture future = new OperationResultFuture();
        pendingCenter.add(streamId, future);

        // 等待建立连接之后再发送消息
        channelFuture.sync();

        channelFuture.channel().writeAndFlush(auth);
        channelFuture.channel().writeAndFlush(msg);

        OperationResult operationResult = future.get();
        System.out.println("获取服务器结果:" + operationResult );
        channelFuture.channel().closeFuture().get();
    }
}
