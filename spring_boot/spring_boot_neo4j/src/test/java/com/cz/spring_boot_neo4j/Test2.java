package com.cz.spring_boot_neo4j;

import com.cz.spring_boot_neo4j.entity2.CompanyGraph;
import com.cz.spring_boot_neo4j.entity2.SupplyGraph;
import com.cz.spring_boot_neo4j.entity2.SupplyRelationship;
import com.cz.spring_boot_neo4j.neo4jmapper.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Test2 {

    @Autowired
    private SupplyGraphRepository supplyGraphRepository;
    @Autowired
    private SupplyRelationshipRepository supplyRelationshipRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    public void testSave() {
        //采购占比
        String scale = "47.14%";
        // 采购金额
        String amount = "18923.42";
        //供应商名称
        String name = "常州常电及其关联公司";
        //公司实体部分及添加公司节点部分省略...(companyGraph)
        SupplyGraph supplyGraph = SupplyGraph.builder().name(name).relationships(new HashSet<>()).build();
        //添加供应商节点
//		supplyGraphRepository.save(supplyGraph);

        CompanyGraph companyGraph = CompanyGraph.builder().companyName("msxf").location("成都").relationships(new HashSet<>()).build();
        String indexName = companyGraph.getCompanyName() + "-" + supplyGraph.getName();
        //供应商关系
        SupplyRelationship supplyRelationship =
                SupplyRelationship.builder().company(companyGraph).supply(supplyGraph).amount(amount).scale(scale).indexName(indexName).build();
        companyGraph.addRelation(supplyGraph, supplyRelationship);
        //添加供应关系
//		supplyRelationshipRepository.save(supplyRelationship);
        companyRepository.save(companyGraph);
        this.testFind();
    }

    @Test
    public void testFind() {
//        Optional<CompanyGraph> com = this.companyRepository.findById(78L);
//        com.ifPresent(e -> {
//            System.out.println(e.getCompanyName());
//            Optional.of(e.getRelationships()).ifPresent(r -> {
//                r.forEach(t -> {
//                    Optional.of(t.getSupply()).ifPresent(y -> {
//                        System.out.println("  |->" + y.getName());
//                    });
//                });
//            });
//        });

    }

}
