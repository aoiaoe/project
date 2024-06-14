package com.cz.es;

import com.alibaba.fastjson.JSONObject;
import com.cz.springbootes.entity.AggTest;
import com.cz.springbootes.es.ElasticsearchUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientAutoConfiguration;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author jzm
 * @date 2024/6/13 : 12:26
 */
public class ElasticSearchUtilsTest {

    RestHighLevelClient client;

    String jh_test = "agg_test";

    @Test
    public void testSearch(){
        Map<String, Object> documentById = ElasticsearchUtils.getDocumentById(client, jh_test, 1);
        System.out.println(documentById);
    }

    @Test
    public void testCreateIndex(){
        ElasticsearchUtils.createIndex(client, jh_test, EsMappings.AGG_TEST_MAPPINGS);
    }

    @Test
    public void testBulkIndex(){

        ElasticsearchUtils.createBulkDocument2(client, jh_test, AggTest.list());
    }

    /**
     * 先按日期分组，再统计每个日期分组中的购买数量的和
     */
    @Test
    public void testAgg(){
        TermsAggregationBuilder agg = new TermsAggregationBuilder("docCountPerMonth");
        agg.field("createMonth");
        SumAggregationBuilder sumAggregationBuilder = new SumAggregationBuilder("SumBuyCount").field("buyCount");
        agg.subAggregation(sumAggregationBuilder);
        MaxAggregationBuilder maxAggregationBuilder = AggregationBuilders.max("maxByCount").field("buyCount");
        agg.subAggregation(maxAggregationBuilder);
        List<? extends Terms.Bucket> buckets = ElasticsearchUtils.aggregationDocument(client, jh_test, agg, null);
        for (Terms.Bucket bucket : buckets) {
            System.out.println(bucket.getClass());
            System.out.println(bucket.getKey() + "_" + bucket.getDocCount());
            // 需要强转为指定类型
            Sum sum = bucket.getAggregations().get("SumBuyCount");
            System.out.println("SumBuyCount: " + sum.getValue());
            Max max = bucket.getAggregations().get("maxByCount");
            System.out.println("maxByCount: " + max.getValue());
            System.out.println("============");
        }
    }

    /**
     * 查询 2021-02 月 buyCount 的总和：
     */
    @Test
    public void testAgg2(){
        SumAggregationBuilder sumAggregationBuilder = AggregationBuilders.sum("buyCountSum").field("buyCount");
        QueryBuilder queryBuilder = QueryBuilders.termQuery("createMonth", "2021-02");
        List<? extends Terms.Bucket> buckets = ElasticsearchUtils.aggregationDocument(client, jh_test, sumAggregationBuilder, queryBuilder);
        for (Terms.Bucket bucket : buckets) {
            System.out.println(bucket.getClass());
            System.out.println(bucket.getKey() + "_" + bucket.getDocCount());
            // 需要强转为指定类型
//            Sum sum = bucket.getAggregations().get("buyCountSum");
//            System.out.println("buyCountSum: " + sum.getValue());
            System.out.println("============");
        }
    }

    @BeforeEach
    public void setUp(){
        RestClientBuilder builder = RestClient.builder(new HttpHost("121.4.79.86", 9200, "http"));
        builder.setHttpClientConfigCallback((httpClientBuilder) -> {
            Credentials credentials = new UsernamePasswordCredentials("elastic", "Sephiroth");
            BasicCredentialsProvider basicCredentialsProvider = new BasicCredentialsProvider();
            basicCredentialsProvider.setCredentials(AuthScope.ANY, credentials);
            httpClientBuilder.setDefaultCredentialsProvider(basicCredentialsProvider);
            return httpClientBuilder;
        });
        client = new RestHighLevelClient(
                builder
        );
    }

    @AfterEach
    public void tearDown() throws IOException {
        client.close();
    }
}
