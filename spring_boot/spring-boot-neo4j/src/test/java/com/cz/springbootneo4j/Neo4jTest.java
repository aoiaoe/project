package com.cz.springbootneo4j;

import com.cz.springbootneo4j.dao.ActorRepo;
import com.cz.springbootneo4j.dao.MovieRepo;
import com.cz.springbootneo4j.domain.Actor;
import com.cz.springbootneo4j.domain.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootNeo4jApplication.class)
public class Neo4jTest {

    @Autowired
    private ActorRepo actorRepo;
    @Autowired
    private MovieRepo movieRepo;

    @Test
    public void testSave(){
        Actor actor = new Actor();
        actor.setId(2L);
        actor.setName("朱一龙");
        actor.setGender("男");
        this.actorRepo.save(actor);
    }

    @Test
    public void testSaveMovie(){
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setName("河边的错误");
        this.movieRepo.save(movie);
    }

    @Test
    public void testSaveRel(){
        this.movieRepo.saveRel("河边的错误", "朱一龙");
    }

    @Test
    public void testSaveRel2(){
        this.actorRepo.saveRel("河边的错误", "朱一龙");
    }

    @Test
    public void testFind(){
        Optional<Movie> byId = this.movieRepo.findById(1L);
        System.out.println(byId.get());
    }
}
