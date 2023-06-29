package com.cz.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class NioServer {

    public static void main(String[] args) throws Exception {
        // 1、打开一个服务端通道
        ServerSocketChannel server = ServerSocketChannel.open();
        // 2、绑定对应的端口号
        server.bind(new InetSocketAddress(9999));
        // 3、通道默认是阻塞的，需要设置为非阻塞
        server.configureBlocking(false);
        // 4、检查是否有客户端传递过来的数据
        while (true) {
            // 5、获取客户端传递过来的数据
            SocketChannel channel = server.accept();
            if(channel == null) {
                System.out.println("     无可读数据...处理其他事情...");
                TimeUnit.SECONDS.sleep(1);
                continue;
            }
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int read = channel.read(byteBuffer);
            System.out.println("获取客户端数据:" + new String(byteBuffer.array(), 0, read));
            // 6、给客户端写回数据
            channel.write(ByteBuffer.wrap("哈哈".getBytes(StandardCharsets.UTF_8)));
            // 7、释放资源
            channel.close();
        }
    }
}
