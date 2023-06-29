package com.cz.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class NioClient {

    public static void main(String[] args) throws Exception {
        // 1、打开数据通道
        SocketChannel client = SocketChannel.open();
        // 2、设置连接ip和端口
        client.connect(new InetSocketAddress("127.0.0.1", 9999));
        // 3、写出数据
        client.write(ByteBuffer.wrap("哼哼".getBytes(StandardCharsets.UTF_8)));
        // 4、读取服务器写回的数据
        ByteBuffer buffer = ByteBuffer.allocate(10);
        int read = client.read(buffer);
        System.out.println("服务器返回:" + new String(buffer.array(), 0, read));
        // 5、释放资源
        client.close();
    }
}
