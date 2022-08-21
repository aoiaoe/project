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
@Getter
@Setter
@ToString
@NodeEntity("ParentNode")
public class ParentNode {

    @Id
    @GeneratedValue
    private Long id;

    private @Property(name = "name")
    String name;

    private @Relationship(type = "RelationEdge")
    Set<RelationNode> sets = new HashSet<>();

    public ParentNode(String name) {
        this.name = name;
    }

    public void addRelation(SonNode sonNode, String name) {
        RelationNode relationNode = new RelationNode(this, name, sonNode);
        sets.add(relationNode);
        sonNode.getSets().add(relationNode);
    }

}