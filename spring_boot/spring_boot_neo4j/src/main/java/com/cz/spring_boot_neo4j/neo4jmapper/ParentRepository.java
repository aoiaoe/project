package com.cz.spring_boot_neo4j.neo4jmapper;

import com.cz.spring_boot_neo4j.entity3.ParentNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ParentRepository extends Neo4jRepository<ParentNode,Long> {
}