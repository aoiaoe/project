package com.cz.client;


import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Slf4j
public class Client {

    private CloseableHttpClient httpClient;
    private RequestConfig requestConfig;

    public Client() {
        this.httpClient = HttpClientBuilder.create().build();
        // ① httpClient 客户端超时时间要大于长轮询约定的超时时间
        this.requestConfig = RequestConfig.custom().setSocketTimeout(40000).build();
    }

    @SneakyThrows
    public void longPolling(String url, String dataId){
        String endpoint = url + "?dataId=" + dataId;
        HttpGet request = new HttpGet(endpoint);
        CloseableHttpResponse response = httpClient.execute(request);
        switch (response.getStatusLine().getStatusCode()) {
            case 200: {
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity()
                        .getContent()));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                response.close();
                String configInfo = result.toString();
                log.info("Tname: [{}], dataId: [{}] changed, receive configInfo: {}", Thread.currentThread().getName(), dataId, configInfo);
                longPolling(url, dataId);
                break;
            }
            // ② 304 响应码标记配置未变更
            case 304: {
                log.info("Tname: [{}], longPolling dataId: [{}] once finished, configInfo is unchanged, longPolling again", Thread.currentThread().getName(), dataId);
                longPolling(url, dataId);
                break;
            }
            default: {
                throw new RuntimeException("unExcepted HTTP status code");
            }
        }
    }

    public static void main(String[] args) {
        Logger logger = (Logger) LoggerFactory.getLogger("org.apache.http");
        logger.setLevel(Level.INFO);
//        logger.setAdditive(false);
        new Thread(() -> {
            Client client = new Client();
            // ③ 对 dataId: user 进行配置监听
            client.longPolling("http://127.0.0.1:7700/listener", "user");
        }, "ClientA").start();
        new Thread(() -> {
            Client client = new Client();
            // ③ 对 dataId: user 进行配置监听
            client.longPolling("http://127.0.0.1:7700/listener", "user");
        }, "ClientB").start();
    }


}
