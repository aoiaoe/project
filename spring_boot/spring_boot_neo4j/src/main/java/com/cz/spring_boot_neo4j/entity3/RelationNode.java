package com.cz.spring_boot_neo4j.entity3;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.neo4j.ogm.annotation.*;

import java.util.Objects;

/**
 * @Author 一一哥Sun
 * @Date Created in 2020/4/10
 * @Description Description
 */
@RelationshipEntity(type = "RelationEdge")
@Getter
@Setter
@ToString
public class RelationNode {

    @Id
    @GeneratedValue
    private Long id;


    private @StartNode
    ParentNode parentNode;

    // 关系名
    private String name;

    private String nick;

    private @EndNode
    SonNode sonNode;

    RelationNode(ParentNode parentNode, String name, SonNode sonNode) {
        this.parentNode = parentNode;
        this.name = name;
        this.sonNode = sonNode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelationNode that = (RelationNode) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}