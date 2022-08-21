package com.cz.spring_boot_neo4j.neo4jmapper;

import com.cz.spring_boot_neo4j.entity3.RelationNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * @author jzm
 * @date 2021/12/13 : 3:08 下午
 */
public interface RelationsNodeRepository extends Neo4jRepository<RelationNode, Long> {
}
