package com.cz.spring_boot_neo4j.entity2;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;

/**
 * 供应商
 */
@NodeEntity
@Builder
@Data
@ToString
public class SupplyGraph {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 供应商名称
     */
    private String name;


    @Relationship(type = "RelationEdge", direction = INCOMING)
    private Set<SupplyRelationship> relationships = new HashSet<>();

}
