package com.cz.socket;

public class HttpResponse {
    private String url;
    private String header;
    private String body;
 
    public String getUrl() {
        return url;
    }
 
    public void setUrl(String url) {
        this.url = url;
    }
 
    public String getHeader() {
        return header;
    }
 
    public void setHeader(String header) {
        this.header = header;
    }
 
    public String getBody() {
        return body;
    }
 
    public void setBody(String body) {
        this.body = body;
    }
 
    @Override
    public String toString() {
        return "HttpResponse{" +
                "url='" + url + '\'' +
                ", header='" + header + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}