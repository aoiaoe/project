package com.cz.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class NioClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        // 连接一个服务器
        socketChannel.connect(new InetSocketAddress(6666));
        if (socketChannel.finishConnect()) {
            while (true) {
                Scanner scanner = new Scanner(System.in);
                String next = scanner.next();
                ByteBuffer wrap = ByteBuffer.wrap(next.getBytes());
                socketChannel.write(wrap);
                ByteBuffer dest = ByteBuffer.allocate(100);
                socketChannel.read(dest);
                System.out.println("服务器说: " + new String(dest.array(), 0, dest.position()));
            }
        }
    }
}
