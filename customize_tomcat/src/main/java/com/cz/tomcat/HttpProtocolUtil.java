package com.cz.tomcat;

import java.nio.charset.StandardCharsets;

public class HttpProtocolUtil {

    public static String getHttpHeader200(long length){
        return "HTTP/1.1 200 OK \n" +
                "Content-Type text/html \n" +
                "Content-Length:" + length + " \n" +
                "\r\n";
    }
    public static String getHttpHeader404(){
        String _404 = "<h1>404 NOT FOUND</h1>";
        return "HTTP/1.1 404 NOT Found\n" +
                "Content-Type text/html\n" +
                "Content-Length:" + _404.getBytes(StandardCharsets.UTF_8).length + "\n" +
                "\r\n" +
                _404;
    }


}
