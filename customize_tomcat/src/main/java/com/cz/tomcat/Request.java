package com.cz.tomcat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;
import java.util.Map;

/**
 * 把请求信息封装程威Request对象(根据inputStream输入流封装)
 *
 * 请求信息如下
 * GET /index.html HTTP/1.1
 * Host: localhost:8080
 * Connection: keep-alive
 * Cache-Control: max-age=0
 * sec-ch-ua: "Not.A/Brand";v="8", "Chromium";v="114", "Google Chrome";v="114"
 * sec-ch-ua-mobile: ?0
 * sec-ch-ua-platform: "Windows"
 * Upgrade-Insecure-Requests: 1
 * User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Request {
    private String method;
    private String url;
//    private Map<String, String> parameters;

    private InputStream inputStream;

    public Request(InputStream in) throws Exception{
        this.inputStream = in;

        // 由于网络io间断性，可能存在请求到了，但是数据还未过来
        int count = 0;
        while (count == 0){
            count = in.available();
        }
        byte[] bytes = new byte[in.available()];
        in.read(bytes);
        String inputStr = new String(bytes);
//        System.out.println("---请求信息--->" + inputStr);

        String firstLine = inputStr.split("\n")[0];
        String[] s = firstLine.split(" ");
        this.method = s[0];
        this.url = s[1];

//        System.out.println("---method--->" + method);
//        System.out.println("---url--->" + url);
    }
}
