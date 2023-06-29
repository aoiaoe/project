package com.cz.tomcat;

import com.cz.tomcat.servlet.HttpServlet;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BootStrap {

    private static int port = 8080;

    /**
     * 服务器的启动入口
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        new BootStrap().start();
    }

    /**
     * 服务器启动初始化操作
     */
    public void start() throws Exception {
        // 加载解析相关的配置， web.xml
        loadServlet();
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("-----> MyTomcat start on port:" + port);

        // 1.0版本，浏览器请求端口，返回固定字符串
//        while (true){
//            Socket socket = serverSocket.accept();
//            OutputStream os = socket.getOutputStream();
//            byte[] bytes = "Hello MyTomcat".getBytes(StandardCharsets.UTF_8);
//            os.write(HttpProtocolUtil.getHttpHeader200(bytes.length).getBytes(StandardCharsets.UTF_8));
//            os.write(bytes);
//            socket.close();
//        }

        // 2.0版本、封装Request和Response对象，返回静态html文件
//        while (true){
//            Socket socket = serverSocket.accept();
//            InputStream in = socket.getInputStream();
//
//            // 封装请求和响应
//            Request request = new Request(in);
//            Response response = new Response(socket.getOutputStream());
//
//            response.outPutHtml(request.getUrl());
//            socket.close();
//        }

        // 3.0版本, 请求动态资源(servlet)
        // 单线程版本，如果某一个请求长时间阻塞，则后续请求也会被阻塞
        // 测试. 在MyServlet#doGet方法中，线程sleep一段时间，A请求servlet被阻塞，在发器B请求html文件，也会被阻塞
//        while (true) {
//            Socket socket = serverSocket.accept();
//            InputStream in = socket.getInputStream();
//
//            // 封装请求和响应
//            Request request = new Request(in);
//            Response response = new Response(socket.getOutputStream());
//
//            HttpServlet httpServlet = servletMap.get(request.getUrl());
//            // 静态资源
//            if(httpServlet == null){
//                response.outPutHtml(request.getUrl());
//            } else {
//                // 动态资源
//                httpServlet.service(request, response);
//            }
//            socket.close();
//        }

        // 4.0版本，增加线程池实现多线程处理请求
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 5, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(100));
        while (true) {
            Socket socket = serverSocket.accept();
            InputStream in = socket.getInputStream();

            // 封装请求和响应
            Request request = new Request(in);
            Response response = new Response(socket.getOutputStream());
            executor.submit(() -> {
                try {
                    HttpServlet httpServlet = servletMap.get(request.getUrl());
                    // 静态资源
                    if (httpServlet == null) {
                        response.outPutHtml(request.getUrl());
                    } else {
                        // 动态资源
                        httpServlet.service(request, response);
                    }
                    socket.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
        }
    }

    private Map<String, HttpServlet> servletMap = new HashMap<>();

    /**
     * 加载解析web.xml初始化
     */
    private void loadServlet() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("web.xml");
        SAXReader saxReader = new SAXReader();
        Document root = saxReader.read(in);
        List<Element> selectNodes = root.selectNodes("//servlet");
        for (int i = 0; i < selectNodes.size(); i++) {
            Element element = selectNodes.get(i);
            Element servletNameEle = ((Element) element.selectSingleNode("servlet-name"));
            String servletName = servletNameEle.getStringValue();
            Element servletClassEle = ((Element) element.selectSingleNode("servlet-class"));
            String servletClass = servletClassEle.getStringValue();

            // 根据servlet-name找到url-pattern
            Element servletMapping = ((Element) root.selectSingleNode("/web-app/servlet-mapping[servlet-name='" + servletName + "']"));
            String mapping = servletMapping.selectSingleNode("url-pattern").getStringValue();

            servletMap.put(mapping, (HttpServlet) Class.forName(servletClass).newInstance());
        }
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        BootStrap.port = port;
    }
}
