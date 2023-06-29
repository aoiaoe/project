package com.cz.tomcat.servlet;

import com.cz.tomcat.Request;
import com.cz.tomcat.Response;

public abstract class HttpServlet implements Servlet{


    public abstract void doGet(Request request, Response response) throws Exception;
    public abstract void doPost(Request request, Response response) throws Exception;

    @Override
    public void service(Request request, Response response) throws Exception {
        if("GET".equals(request.getMethod())){
            doGet(request, response);
        }
        doPost(request, response);

    }
}
