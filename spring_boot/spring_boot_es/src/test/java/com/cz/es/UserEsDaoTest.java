package com.cz.es;

import com.cz.springbootes.SpringBootEsApplication;
import com.cz.springbootes.dao.UserEsDao;
import com.cz.springbootes.entity.UserEs;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

@SpringBootTest(classes = SpringBootEsApplication.class)
public class UserEsDaoTest {

    @Autowired
    private UserEsDao userEsDao;
    @Autowired
    private ElasticsearchRestTemplate restTemplate;

    /**
     * 因为UserEsh实体类注解设置了自动创建，项目启动会自动创建索引
     */
    @Test
    public void testCreateIndexAuto() {

    }

    /**
     *  description字段使用的ik_max_word分词
     *     如果在查询的时候，查询条件未指定分词器，那么默认会使用description字段的分词器
     *     论据: 因为description使用ik分词器，那么如果我们指定standard分词器，就会查询不出结果
     */
    @Test
    public void testSearch(){
        MatchQueryBuilder query = QueryBuilders.matchQuery("description", "国人");
        query.analyzer("standard");// 若指定此分词器，就和字段的分词器不同，则查询不出结果
        Iterable<UserEs> search = this.userEsDao.search(query);
        for (UserEs userEs : search) {
            System.out.println(userEs);
        }
    }

    @Test
    public void testCreateDoc() {
        UserEs userEs = UserEs.builder().id(1L).name("JZM").age(22).sex("男").description("我是一个中国人").build();
        userEsDao.save(userEs);
    }

}
