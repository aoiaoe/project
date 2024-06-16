package com.cz.io;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class NioTest {

    @Test
    public void test1(){
        ByteBuffer buffer = ByteBuffer.allocate(10);
        for (int i = 0; i < 5; i++) {
            buffer.put((byte)(67 + i));
        }
        disp(buffer, "写入五个数据");

        int pos = buffer.position();
        int lim = buffer.limit();
        buffer.flip();
        disp(buffer, "第一次翻转读");

        for (int i = 0; i < 5; i++) {
            System.out.println(buffer.get());
        }
        disp(buffer, "第一次读取五个数据之后");

//        buffer.position(pos-2);
        buffer.limit(lim);
        disp(buffer, "重新更新pos和limit继续写");

        for (int i = 5; i < 10; i++) {
            buffer.put((byte)(67 + i));
        }
        disp(buffer, "继续写入五个数据之后");

        buffer.flip();
        disp(buffer, "第二次翻转");

        for (int i = 0; i < buffer.limit(); i++) {
            System.out.println(buffer.get());
        }
        disp(buffer, "读取所有数据");
    }


    public void disp(ByteBuffer buffer, String...msg){
        if(msg != null) {
            System.out.println(msg[0]);
        }
        System.out.println("pos :" + buffer.position());
        System.out.println("lim :" + buffer.limit());
        System.out.println("cap :" + buffer.capacity());
        System.out.println("---------");
    }

    @Test
    public void discardServer() throws IOException {
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(18222));
        server.configureBlocking(false);

        Selector selector = Selector.open();
        server.register(selector, SelectionKey.OP_ACCEPT);

        while (true){
            int select = selector.select();
            if(select > 0){
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()){
                    SelectionKey next = iterator.next();
                    if(next.isAcceptable()){
                        System.out.println("有人连我了！");
                        SocketChannel channel = server.accept();
                        channel.configureBlocking(false);
                        channel.register(selector, SelectionKey.OP_READ);
                    } else if(next.isReadable()){
                        try {
                            System.out.println("有人写数据了！");
                            SocketChannel channel = (SocketChannel) next.channel();
                            ByteBuffer buffer = ByteBuffer.allocate(100);
                            int len = -1;
                            while ((len = channel.read(buffer)) > 0) {
                                buffer.flip();
                                System.out.println(new String(buffer.array(), 0, len, StandardCharsets.UTF_8));
                                buffer.clear();
                            }
                            if(len == -1){
                                System.out.println("客户端正常断开");
                                next.cancel();
                            }
                        } catch (Exception e){
                            System.out.println("客户端强制断开");
                            next.cancel();
                        }
                    }
                    iterator.remove();
                }
            }
        }
    }

    @Test
    public void discardClient() throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.connect(new InetSocketAddress("localhost", 18222));
        channel.configureBlocking(false);
        // 自旋
        if(!channel.finishConnect()) {
        }
        byte[] bytes = "服务器你好".getBytes(StandardCharsets.UTF_8);
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        channel.write(buffer);
        channel.close();

    }
}
