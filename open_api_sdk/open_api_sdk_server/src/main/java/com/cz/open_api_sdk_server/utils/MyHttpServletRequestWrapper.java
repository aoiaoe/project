package com.cz.open_api_sdk_server.utils;

import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 通过过滤器可以包装request，达到修改request内容的目的
 */
public class MyHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private byte[] requestBody;

    public MyHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        requestBody = ParamHolder.get();
//        requestBody = IOUtils.toByteArray(request.getInputStream());
//       	// 这里做request处理逻辑
//        }
    }


    @Override
    public ServletInputStream getInputStream() {
    	// 返回的是我们处理之后的数据
        final ByteArrayInputStream bais = new ByteArrayInputStream(requestBody);
        return new ServletInputStream() {
            @Override
            public int read() {
                return bais.read();  // 读取 requestBody 中的数据
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

}
