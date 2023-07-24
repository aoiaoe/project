package com.cz.socket;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Socket2Tomcat {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress("127.0.0.1", 18104));

            BufferedWriter os = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String reqString = "GET /test/env?name=JAVA_HOME HTTP/1.1\r\n" +
                    "Accept:text/html,application/xhtml+xml\r\n" +
                    "Accept-Language:zh-CN,zh;q=0.9\r\n" +
                    "\r\n";
            os.write(reqString);
            os.flush();

            InputStream in = socket.getInputStream();
            int available = in.available();

            int count = 0;
            while (count == 0){
                count = in.available();
            }
            byte[] bytes = new byte[count];
            in.read(bytes);
            System.out.println("获取结果" + new String(bytes));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
