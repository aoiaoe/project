package com.cz.server;

import lombok.Data;

import javax.servlet.AsyncContext;

@Data
public class AsyncTask {
    // 长轮询请求的上下文，包含请求和响应体
    private AsyncContext asyncContext;
    // 超时标记
    private boolean timeout;

    public AsyncTask(AsyncContext asyncContext, boolean timeout) {
        this.asyncContext = asyncContext;
        this.timeout = timeout;
    }
}