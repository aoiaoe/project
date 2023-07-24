package com.cz.socket;

import com.alibaba.fastjson.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class TcpClient {
    public static void main(String[] args) throws IOException {
        Socket socket = null;
        OutputStream out = null;
        InputStream in = null;
        ByteArrayOutputStream byteOut = null;
        try {
            //服务器ip地址和端口
            socket = new Socket("127.0.0.1",18104);
            //输出流
            out = socket.getOutputStream();
            //构造http请求
            StringBuilder builder = new StringBuilder();
            //header信息
            Map<String,String> map = new HashMap<>();
            //文本类型
            map.put("Content-Type","text/html;charset=utf-8");
            //保持连接
            map.put("Connection","keep-alive");
            map.put("Host","localhost");
            //请求行
            builder.append("GET /test/env?name=JAVA_HOME HTTP/1.1\r\n");
            //请求头部
            for(Map.Entry<String,String> entry:map.entrySet()){
                builder.append(entry.getKey() + ":" +entry.getValue() + "\r\n");
            }
            //请求空行
            builder.append("\r\n");
            out.write(builder.toString().getBytes());
            //输入流
            in = socket.getInputStream();
            byteOut = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int len = -1;
            // 会被in.read阻塞
//            while ((len = in.read(bytes)) != -1){
                //写入新建流中
                len = in.read(bytes);
                byteOut.write(bytes,0,len);
//            }
            //以响应空行为标志，将响应信息分为两个部分
            String[] str = new String(byteOut.toByteArray()).split("\r\n\r\n");
            //以空行为标志，将响应行和响应头部分开
            String[] urlAndHeader = str[0].split("\r\n");
            HttpResponse  httpResponse = new HttpResponse();
            //响应行
            httpResponse.setUrl(urlAndHeader[0]);
            StringBuilder headers = new StringBuilder();
            for (int i = 1; i < urlAndHeader.length; i++){
                //拼接响应头部
                headers.append(urlAndHeader[i] + "\r\n");
            }
            //响应头部
            httpResponse.setHeader(headers.toString());
            //响应文本
            httpResponse.setBody(str[1]);
            System.out.println(JSONObject.toJSONString(httpResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (byteOut != null){
                byteOut.close();
            }
            if (out != null){
                out.close();
            }
            if (in != null){
                in.close();
            }
            if (socket != null){
                //释放资源
                socket.close();
            }
        }
    }
}