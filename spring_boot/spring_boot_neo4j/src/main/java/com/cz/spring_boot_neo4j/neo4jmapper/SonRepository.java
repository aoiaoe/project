package com.cz.spring_boot_neo4j.neo4jmapper;

import com.cz.spring_boot_neo4j.entity3.ParentNode;
import com.cz.spring_boot_neo4j.entity3.SonNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface SonRepository extends Neo4jRepository<SonNode,Long> {
}