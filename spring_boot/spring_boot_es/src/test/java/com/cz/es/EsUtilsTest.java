package com.cz.es;

import com.cz.springbootes.es.ElasticsearchUtils;
import lombok.SneakyThrows;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Max;
import org.elasticsearch.search.aggregations.metrics.MaxAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Stats;
import org.elasticsearch.search.aggregations.metrics.Sum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class EsUtilsTest {

    RestHighLevelClient client;

    private String INDEX = "agg_test";


    @Test
    public void testAggs1(){
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("monthGroup").field("createMonth");
        termsAggregationBuilder.subAggregation(AggregationBuilders.max("MaxCount").field("buyCount"));
        termsAggregationBuilder.subAggregation(AggregationBuilders.sum("SumCount").field("buyCount"));
        termsAggregationBuilder.subAggregation(AggregationBuilders.stats("StatCount").field("buyCount"));
        List<? extends Terms.Bucket> buckets = ElasticsearchUtils.aggTerms(client, INDEX, termsAggregationBuilder);
        for (Terms.Bucket bucket : buckets) {
            System.out.println(bucket.getKey() + "----" + bucket.getDocCount());
            Max max = bucket.getAggregations().get("MaxCount");
            Sum sum = bucket.getAggregations().get("SumCount");
            Stats stats = bucket.getAggregations().get("StatCount");
            System.out.println("最大:" + max.getValue());
            System.out.println("总计:" + sum.getValue());
            System.out.println(String.format("多维度:最大:%s, 最小:%s, 平均:%s, 总和:%s, 计数:%s", stats.getMax(),
                    stats.getMin(), stats.getAvg(), stats.getSum(), stats.getCount()));
        }
    }

    /**
     * 统计2021-03月最大购买数
     */
    @Test
    public void testAggs(){
        MaxAggregationBuilder maxAggregationBuilder = AggregationBuilders.max("maxBuyCount").field("buyCount");
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("createMonth", "2021-03");
        Aggregations aggregations = ElasticsearchUtils.agg(client, INDEX, maxAggregationBuilder, termQueryBuilder);
        Max max = aggregations.get("maxBuyCount");
        System.out.println("2021-03月最大购买数:" + max.getValue());
    }

    @BeforeEach
    public void setUp(){
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("elastic", "Sephiroth");
        provider.setCredentials(AuthScope.ANY, credentials);
        RestClientBuilder builder = RestClient.builder(new HttpHost("121.4.79.86", 9200));
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
    public void close(){
        client.close();
    }
}
