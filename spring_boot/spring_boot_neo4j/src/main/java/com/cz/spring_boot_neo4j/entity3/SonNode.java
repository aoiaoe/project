package com.cz.spring_boot_neo4j.entity3;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.neo4j.ogm.annotation.*;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author 一一哥Sun
 * @Date Created in 2020/4/10
 * @Description Description
 */
@NodeEntity("SonNode")
@Getter
@Setter
public class SonNode {

    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "name")
    private String name;

    private @Relationship(type = "RelationEdge", direction = "INCOMING")
    Set<RelationNode> sets = new HashSet<>();

    public SonNode(String name) {
        this.name = name;
    }


}