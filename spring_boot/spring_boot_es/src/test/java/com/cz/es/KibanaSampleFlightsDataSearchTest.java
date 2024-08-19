package com.cz.es;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;

public class KibanaSampleFlightsDataSearchTest {

    @Test
    public void testSearch() throws URISyntaxException {

        // 认证
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

        try (RestHighLevelClient client =
                     new RestHighLevelClient(builder)) {


            // 查询从威尼斯飞到中国的价格最低的10张票，价格升序
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            boolQueryBuilder.must(QueryBuilders.termQuery("OriginCityName", "Venice"));
            boolQueryBuilder.must(QueryBuilders.termQuery("DestCountry", "CN"));

            SearchSourceBuilder source = new SearchSourceBuilder();
            source.query(boolQueryBuilder);
            source.sort("AvgTicketPrice", SortOrder.ASC);

            source.from(0);
            source.size(10);

            SearchRequest request = new SearchRequest("kibana_sample_data_flights");
            request.source(source);

            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            SearchHit[] hits = response.getHits().getHits();
            for (SearchHit hit : hits) {
                System.out.println(hit.getSourceAsString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
