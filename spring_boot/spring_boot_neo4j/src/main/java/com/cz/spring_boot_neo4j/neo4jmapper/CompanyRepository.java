package com.cz.spring_boot_neo4j.neo4jmapper;

import com.cz.spring_boot_neo4j.entity2.CompanyGraph;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface CompanyRepository extends Neo4jRepository<CompanyGraph, Long> {
}
