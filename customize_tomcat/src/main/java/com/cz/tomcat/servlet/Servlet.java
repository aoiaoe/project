package com.cz.tomcat.servlet;

import com.cz.tomcat.Request;
import com.cz.tomcat.Response;

public interface Servlet {

    void init() throws Exception;
    void destroy() throws Exception;
    void service(Request request, Response response) throws Exception;

}
