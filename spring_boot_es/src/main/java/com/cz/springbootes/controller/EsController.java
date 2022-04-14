package com.cz.springbootes.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.cz.springbootes.es.ElasticsearchUtils;
import com.cz.springbootes.vo.EsVo;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author jzm
 * @date 2022/4/14 : 11:23
 */
@RestController
@RequestMapping(value = "/es")
public class EsController {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @GetMapping
    public Object search(String value){
        MatchPhraseQueryBuilder query = new MatchPhraseQueryBuilder("word", value);
        return ElasticsearchUtils.searchDocumentPage(restHighLevelClient, "test_id_max_search",
                query, 0, 10, new TypeReference<EsVo>(){});
    }

    @GetMapping(value = "/updateByQuery")
    public boolean updateByQuery(){
        // 使用脚本将status=0修改为status=1
        TermQueryBuilder query = QueryBuilders.termQuery("status", 1);
        Script script = new Script("ctx.source['status']=0");
        ElasticsearchUtils.updateBulkDocumentByQuery(restHighLevelClient, "test_id_max_search",
                query, script);
        return true;
    }
}
