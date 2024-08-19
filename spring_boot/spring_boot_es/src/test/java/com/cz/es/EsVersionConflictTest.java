package com.cz.es;

import lombok.SneakyThrows;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class EsVersionConflictTest {

    RestHighLevelClient client;

    private String index = "version_conflict_idx_0002";

    @Test
    public void testBulkUpdate() throws Exception {
        while (true) {
            try {
                String msg = createRandomString(10);
                String doc = "{\"foo\":\"" + msg + "\",\"name\":\"update\"}";
                BulkRequest req = new BulkRequest(index)
                        .add(new UpdateRequest().id("1").doc(doc, XContentType.JSON))
                        .add(new UpdateRequest().id("2").doc(doc, XContentType.JSON))
                        .add(new UpdateRequest().id("3").doc(doc, XContentType.JSON));
                BulkResponse bulk = client.bulk(req, RequestOptions.DEFAULT);
//                System.out.println(bulk.getItems());
                TimeUnit.MILLISECONDS.sleep(199);
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
    }

    @Test
    public void testUpdateByQuery() throws Exception {
        while (true) {
            try {
                String msg = createRandomString(10);
                String doc = "{\"foo\":\"" + msg + "\",\"name\":\"update\"}";
                UpdateByQueryRequest queryRequest = new UpdateByQueryRequest();
                queryRequest.indices(index);
                MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("name", "update");
                queryRequest.setQuery(queryBuilder);
                queryRequest.setScript(new Script("ctx._source[\"foo\"]=\"" + msg + "\""));
                BulkByScrollResponse response = client.updateByQuery(queryRequest, RequestOptions.DEFAULT);
                System.out.println(response.getUpdated());
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
    }

    char[] arr = "0123456789abcdefghijklmnopqrstuvwxtzABCDEFEHIJKLMNOPQRSTUVWXYZ".toCharArray();
    Random random = new Random();

    private String createRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(arr[random.nextInt(62)]);
        }
        return sb.toString();
    }


    @BeforeEach
    public void setUp() {
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("elastic", "Sephiroth");
        provider.setCredentials(AuthScope.ANY, credentials);
        RestClientBuilder builder = RestClient.builder(new HttpHost("tx-sh", 9200));
        builder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                httpClientBuilder.disableAuthCaching();
                return httpClientBuilder.setDefaultCredentialsProvider(provider);
            }
        }).setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
            @Override
            public RequestConfig.Builder customizeRequestConfig(
                    RequestConfig.Builder requestConfigBuilder) {
                return requestConfigBuilder.setConnectTimeout(5000 * 1000) // 连接超时（默认为1秒）
                        .setSocketTimeout(6000 * 1000);// 套接字超时（默认为30秒）
            }
        });
        client =
                new RestHighLevelClient(builder);
    }

    @SneakyThrows
    @AfterEach
    public void close() {
        client.close();
    }
}
