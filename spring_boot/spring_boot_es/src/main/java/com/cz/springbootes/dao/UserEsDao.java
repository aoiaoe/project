package com.cz.springbootes.dao;

import com.cz.springbootes.entity.UserEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserEsDao extends ElasticsearchRepository<UserEs, Long> {
}
