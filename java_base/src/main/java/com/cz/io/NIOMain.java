package com.cz.io;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class NIOMain {

    /**
     * 测试文件读写
     * 分散读,聚集写
     * @throws IOException
     */
    @Test
    public void test4() throws IOException {
        // 打开通道
        FileChannel inChannel = FileChannel.open(Paths.get("src/main/java/com/cz/io/test4.txt"), StandardOpenOption.READ);

        ByteBuffer buf1 = ByteBuffer.allocate(10);
        ByteBuffer buf2 = ByteBuffer.allocate(20);

        ByteBuffer[] bufs = {buf1, buf2};

        inChannel.read(bufs);

        for (ByteBuffer buf : bufs) {
            buf.flip();
        }

        byte[] bytes = new byte[10];
        buf1.get(bytes);
        System.out.println(new String(bytes, 0, buf1.limit()));
        bytes = new byte[20];
        buf2.get(bytes);
        System.out.println(new String(bytes, 0, buf2.limit()));
        buf1.position(0).limit(1);
        buf2.position(0).limit(2);
        RandomAccessFile randomAccessFile = new RandomAccessFile("src/main/java/com/cz/io/test4_copy.txt", "rw");
        FileChannel outChannel = randomAccessFile.getChannel();
        outChannel.write(bufs);
        outChannel.close();
        inChannel.close();
    }

    @Test
    public void test3(){
        // 创建一个缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        // 看一下初始时4个核心变量的值
        System.out.println("初始时-->limit--->"+byteBuffer.limit());
        System.out.println("初始时-->position--->"+byteBuffer.position());
        System.out.println("初始时-->capacity--->"+byteBuffer.capacity());
        System.out.println("初始时-->mark--->" + byteBuffer.mark());

        System.out.println("--------------------------------------");
        // 添加一些数据到缓冲区中
        String s = "Jzm";
        byteBuffer.put(s.getBytes());

        // 看一下初始时4个核心变量的值
        System.out.println("put完之后-->limit--->"+byteBuffer.limit());
        System.out.println("put完之后-->position--->"+byteBuffer.position());
        System.out.println("put完之后-->capacity--->"+byteBuffer.capacity());
        System.out.println("put完之后-->mark--->" + byteBuffer.mark());

        System.out.println("--------------------------------------");
        byteBuffer.flip();
        // 看一下初始时4个核心变量的值
        System.out.println("flip完之后-->limit--->"+byteBuffer.limit());
        System.out.println("flip完之后-->position--->"+byteBuffer.position());
        System.out.println("flip完之后-->capacity--->"+byteBuffer.capacity());
        System.out.println("flip完之后-->mark--->" + byteBuffer.mark());

        System.out.println("--------------------------------------");
        byte[] bytes = new byte[byteBuffer.limit()];
        byteBuffer.get(bytes);
        System.out.println(new String(bytes, 0 , bytes.length));

        // 看一下初始时4个核心变量的值
        System.out.println("get完之后-->limit--->"+byteBuffer.limit());
        System.out.println("get完之后-->position--->"+byteBuffer.position());
        System.out.println("get完之后-->capacity--->"+byteBuffer.capacity());
        System.out.println("get完之后-->mark--->" + byteBuffer.mark());

        System.out.println("--------------------------------------");
        byteBuffer.clear();
        // 看一下初始时4个核心变量的值
        System.out.println("clear完之后-->limit--->"+byteBuffer.limit());
        System.out.println("clear完之后-->position--->"+byteBuffer.position());
        System.out.println("clear完之后-->capacity--->"+byteBuffer.capacity());
        System.out.println("clear完之后-->mark--->" + byteBuffer.mark());

        System.out.println("--------------------------------------");
        s = "Jzm";
        byteBuffer.put(s.getBytes());

        // 看一下初始时4个核心变量的值
        System.out.println("put完之后-->limit--->"+byteBuffer.limit());
        System.out.println("put完之后-->position--->"+byteBuffer.position());
        System.out.println("put完之后-->capacity--->"+byteBuffer.capacity());
        System.out.println("put完之后-->mark--->" + byteBuffer.mark());

    }

    //阻塞式
    @Test
    public void nio1() {
        try {
            RandomAccessFile file = new RandomAccessFile("src/main/java/com/cz/io/text.txt", "r");
            FileChannel channel = file.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            channel.read(byteBuffer);
            //limit 和 position 可以用flip一句代码解决
//            byteBuffer.limit(byteBuffer.position());
//            byteBuffer.position(0);
            byteBuffer.flip();
            System.out.println(Charset.defaultCharset().decode(byteBuffer));
            byteBuffer.clear();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void nio2() {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(99));
            //非阻塞 start -> 以下代码为非阻塞配置代码
            serverSocketChannel.configureBlocking(false);//非阻塞
            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                selector.select();//阻塞
                // 错误，应该使用迭代器，并在处理完key之后，使用迭代器remove掉，防止重复处理
                for (SelectionKey key : selector.selectedKeys()) {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    while (socketChannel.read(byteBuffer) != -1) {
                        byteBuffer.flip();
                        socketChannel.write(byteBuffer);
                        byteBuffer.clear();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}