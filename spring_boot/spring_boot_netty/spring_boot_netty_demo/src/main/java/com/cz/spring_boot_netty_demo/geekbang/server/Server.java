package com.cz.spring_boot_netty_demo.geekbang.server;

import com.cz.spring_boot_netty_demo.geekbang.server.codec.OrderFrameDecode;
import com.cz.spring_boot_netty_demo.geekbang.server.codec.OrderFrameEncoder;
import com.cz.spring_boot_netty_demo.geekbang.server.codec.OrderProtocolDecoder;
import com.cz.spring_boot_netty_demo.geekbang.server.codec.OrderProtocolEncoder;
import com.cz.spring_boot_netty_demo.geekbang.server.handler.AuthHandler;
import com.cz.spring_boot_netty_demo.geekbang.server.handler.MetricsHandler;
import com.cz.spring_boot_netty_demo.geekbang.server.handler.OrderServerProcessHandler;
import com.cz.spring_boot_netty_demo.geekbang.server.handler.ServerIdleCheckHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ipfilter.IpFilterRule;
import io.netty.handler.ipfilter.IpFilterRuleType;
import io.netty.handler.ipfilter.IpSubnetFilterRule;
import io.netty.handler.ipfilter.RuleBasedIpFilter;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.UnorderedThreadPoolEventExecutor;

import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;
import java.util.concurrent.ExecutionException;

public class Server {

    public static void main(String[] args) throws InterruptedException, ExecutionException, CertificateException, SSLException {
        final ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup());
        // 日志处理器
        serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));

        final MetricsHandler metricsHandler = new MetricsHandler();

        final UnorderedThreadPoolEventExecutor bizThread = new UnorderedThreadPoolEventExecutor(10, new DefaultThreadFactory("bizThread"));

        // cidr 无类别域间路由, 前面8位和配置相同的ip会被拒绝
        // 因为本地客户端是127.0.0.1,前八位肯定相同，所以会被拒绝
        IpFilterRule rule1 = new IpSubnetFilterRule("127.1.0.1", 8, IpFilterRuleType.REJECT);
        // cidr 无类别域间路由, 前面16位和配置相同的ip会被拒绝
        IpFilterRule rule2 = new IpSubnetFilterRule("127.1.0.1", 16, IpFilterRuleType.REJECT);
        RuleBasedIpFilter ruleBasedIpFilter = new RuleBasedIpFilter(rule2);

        // 授权处理器
        final AuthHandler authHandler = new AuthHandler();

        // ssl证书
        SelfSignedCertificate selfSignedCertificate = new SelfSignedCertificate();
        System.out.println(selfSignedCertificate.certificate());

        SslContext sslContext = SslContextBuilder.forServer(selfSignedCertificate.certificate(), selfSignedCertificate.privateKey()).build();


        serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ch.pipeline()
                        // pipeline是一个调用链，有来有回
                        // 所以handler必须放最后一个

                        // 反问黑名单过滤
                        .addLast(ruleBasedIpFilter)
                        .addLast("metrics", metricsHandler)
                        .addLast("idleCheck", new ServerIdleCheckHandler())
                        .addLast("ssl", sslContext.newHandler(ch.alloc()))
                        .addLast(new OrderFrameDecode())
                        .addLast(new OrderFrameEncoder())
                        .addLast(new OrderProtocolDecoder())
                        .addLast(new OrderProtocolEncoder())
                        .addLast(new LoggingHandler(LogLevel.INFO))
                        // 授权处理器
                        .addLast(authHandler)
                        // 使用独立的线程池处理业务逻辑
                        .addLast(bizThread, new OrderServerProcessHandler())

                ;

            }
        });

        final ChannelFuture sync = serverBootstrap.bind(8090).sync();
        sync.channel().closeFuture().get();

    }
}
