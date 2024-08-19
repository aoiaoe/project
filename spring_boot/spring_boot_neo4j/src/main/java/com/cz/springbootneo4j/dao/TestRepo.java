package com.cz.springbootneo4j.dao;

import com.cz.springbootneo4j.domain.Movie;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface TestRepo  extends Neo4jRepository<Movie, Long>{

    @Query("match (p:PersonLoan) return p.type limit 4")
    Optional<List<String>> getLoanTypeName();
}
