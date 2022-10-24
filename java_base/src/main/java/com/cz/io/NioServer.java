package com.cz.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioServer {
    public static void main(String[] args) throws IOException {
        // 建立一个serversocketchannel
        ServerSocketChannel sever = ServerSocketChannel.open();
        // 非阻塞的通道的配置
        sever.configureBlocking(false);
        // 绑定端口
        sever.bind(new InetSocketAddress(6666));
        // 创建一个IO多路复用器
        Selector selector = Selector.open();
        //  注册并监控 感兴趣的事情
        sever.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            // 阻塞的方法。返回值代表发生事件的通道的个数
            // 0-超时   -1- 错误
            // 如果返回零，没有消息,阻塞的方法
            int select = selector.select();
            if (select == 0) {
                continue;
            }
            // 拿到所有的事件，只要走到了这里，必然说明发生了事情，有可读，可写，可连接的channel
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                // 拿到这个事件
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    System.out.println("有人连我了！");
                    // 三次握手建立tcp连接
                    SocketChannel accept = sever.accept();
                    accept.configureBlocking(false);
                    // 建立好连接以后，注册到selector
                    accept.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    channel.read(buffer);
                    System.out.println(new String(buffer.array(), 0, buffer.position()));
                    buffer.clear();
                }
                iterator.remove();
            }
        }
    }
}   

