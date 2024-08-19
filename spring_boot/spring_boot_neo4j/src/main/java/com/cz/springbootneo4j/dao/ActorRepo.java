package com.cz.springbootneo4j.dao;

import com.cz.springbootneo4j.domain.Actor;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

public interface ActorRepo extends Neo4jRepository<Actor, Long> {

    @Query("match (m:Movie),(a:Actor) where m.name=$movie and a.name =$actor CREATE (a) -[r:acted{time:2023-11}]-> (m);")
    public void saveRel(@Param("movie") String movie, @Param("actor") String actor);
}
