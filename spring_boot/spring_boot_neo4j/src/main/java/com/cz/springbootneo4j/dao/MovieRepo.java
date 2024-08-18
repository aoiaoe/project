package com.cz.springbootneo4j.dao;

import com.cz.springbootneo4j.domain.Movie;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

public interface MovieRepo  extends Neo4jRepository<Movie, Long> {


    @Query("match (m:Movie),(a:Actor) where m.name=$movie and a.name =$actor CREATE (m) -[r:act{time:2023}]-> (a);")
    public void saveRel(@Param("movie") String movie, @Param("actor") String actor);
}
