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

/**
 * @author jzm
 * @date 2021/12/10 : 4:46 下午
 */
@NodeEntity
@Builder
@Data
@ToString
public class CompanyGraph {

    @Id
    @GeneratedValue
    private Long id;

    private String companyName;

    private String location;

    @Relationship(type = "RelationEdge")
    private Set<SupplyRelationship> relationships = new HashSet<>();

    public void addRelation(SupplyGraph supplyGraph, SupplyRelationship relationship){
        relationships.add(relationship);
        supplyGraph.getRelationships().add(relationship);
    }
}
