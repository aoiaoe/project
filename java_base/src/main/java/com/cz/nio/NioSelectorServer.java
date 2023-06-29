package com.cz.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class NioSelectorServer {

    public static void main(String[] args) throws Exception {
        // 1、打开一个服务端通道
        ServerSocketChannel server = ServerSocketChannel.open();
        // 2、绑定对应的端口号
        server.bind(new InetSocketAddress(9999));
        // 3、通道默认是阻塞的，需要设置为非阻塞
        server.configureBlocking(false);
        // 4、创建选择器
        Selector selector = Selector.open();
        //5、将服务端通道注册到选择器，并指定注册监听的时间为OP_ACCEPT
        server.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            // 6、检查选择器是否有时间
            int select = selector.select(2000);
            if(select == 0){
                System.out.println("   无事件发生....");
                continue;
            }
            // 7、获取事件集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while(iterator.hasNext()){
                // 判断事件是否是客户端连接事件
                SelectionKey key = iterator.next();
                if(key.isAcceptable()){
                    // 得到客户端通道，并注册到选择器上，并制定监听事件为ON_READ
                    SocketChannel socketChannel = server.accept();
                    System.out.println("有客户端连接....");
                    // 通道必须设置为非阻塞的状态，因为selector会轮训通道事件
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    // 10、判断是否客户端读就绪事件
                } else if(key.isReadable()) {
                    // 11、得到客户端通道，读取数据到缓冲区
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int read = socketChannel.read(byteBuffer);
                    if(read > 0) {
                        System.out.println("获取客户端数据:" + new String(byteBuffer.array(), 0, read));
                    }
                    // 12、给客户端写回数据
                    socketChannel.write(ByteBuffer.wrap("哈哈".getBytes(StandardCharsets.UTF_8)));
                    socketChannel.close();
                }
            }
            // 13、从集合中删除时间，防止重复处理
            iterator.remove();

        }
    }
}
