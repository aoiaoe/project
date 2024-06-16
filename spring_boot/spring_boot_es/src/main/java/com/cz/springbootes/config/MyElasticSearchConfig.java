package com.cz.springbootes.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

// 如果项目中没有自动装配 RestHighLevelClient , 则需要继承 AbstractElasticsearchConfiguration 注入 RestHighLevelClient
//@Configuration
public class MyElasticSearchConfig extends AbstractElasticsearchConfiguration {


    @Override
    public RestHighLevelClient elasticsearchClient() {
        return null;
    }
}
