package com.cz.tomcat.servlet;

import com.cz.tomcat.HttpProtocolUtil;
import com.cz.tomcat.Request;
import com.cz.tomcat.Response;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class MyServlet extends HttpServlet{

    @Override
    public void doGet(Request request, Response response) throws Exception {
        System.out.println("开始睡眠:" + Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(100);
        String content = "<h1>MyServlet doGet</h1>";
        response.output(HttpProtocolUtil.getHttpHeader200(content.getBytes(StandardCharsets.UTF_8).length) + content);
    }

    @Override
    public void doPost(Request request, Response response) throws Exception {
        String content = "<h1>MyServlet doPost</h1>";
        response.output(HttpProtocolUtil.getHttpHeader200(content.getBytes(StandardCharsets.UTF_8).length) + content);
    }

    @Override
    public void init() throws Exception {

    }

    @Override
    public void destroy() throws Exception {

    }
}
