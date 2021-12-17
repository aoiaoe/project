package com.cz.spring_boot_neo4j;

import com.alibaba.fastjson.JSONObject;
import com.cz.spring_boot_neo4j.entity3.ParentNode;
import com.cz.spring_boot_neo4j.entity3.RelationNode;
import com.cz.spring_boot_neo4j.entity3.SonNode;
import com.cz.spring_boot_neo4j.neo4jmapper.ParentRepository;
import com.cz.spring_boot_neo4j.neo4jmapper.RelationsNodeRepository;
import com.cz.spring_boot_neo4j.neo4jmapper.SonRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Value;
import org.neo4j.driver.internal.InternalNode;
import org.neo4j.driver.internal.InternalRecord;
import org.neo4j.driver.internal.value.NodeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Test3 {

    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private SonRepository sonRepository;
    @Autowired
    private Session neo4jSession;

    @Autowired
    private RelationsNodeRepository relationsNodeRepository;

    @Test
    public void testSave() {
        SonNode sonNode1 = new SonNode("儿子小帅");
        SonNode sonNode2 = new SonNode("女儿小美");

        ParentNode parentNode = new ParentNode("爸爸:孙一一");

        parentNode.addRelation(sonNode1, "女儿");
        parentNode.addRelation(sonNode2, "儿子");

        parentRepository.save(parentNode);


        this.testFindParent();
        this.testFindSon();
    }

    @Test
    public void testFindSon() {
        Iterable<SonNode> sonNodes = this.sonRepository.findAll();
        for (SonNode sonNode : sonNodes) {
            Set<RelationNode> relationNodeSet = sonNode.getSets();
            for (RelationNode relationNode : relationNodeSet) {
                log.info("id:" + sonNode.getId() + " name:" + sonNode.getName() + " 关系：" + relationNode.getName() + "父节点：" + relationNode.getParentNode().getName());
            }
        }

    }

    @Test
    public void testFindParent() {
        Iterable<ParentNode> parentNodes = parentRepository.findAll();
        for (ParentNode parentNode : parentNodes) {
            Set<RelationNode> relationNodeSet = parentNode.getSets();
            for (RelationNode relationNode : relationNodeSet) {
                log.info("id:" + parentNode.getId() + " name:" + parentNode.getName() + " 关系：" + relationNode.getName() + "子节点：" + relationNode.getSonNode().getName());
            }
        }
    }

    @Test
    public void testCypher(){
        String sql = "match (n:学生) return n";
        Result run = this.neo4jSession.run(sql, new HashMap<>());
        for (Record record : run.list()) {
            InternalRecord r = (InternalRecord)record;
            for (Value value : r.values()) {
                System.out.println(value.asNode().asMap());
            }
        }
        neo4jSession.close();
    }

    @Test
    public void testSelectById(){
        List<Long> ids = new ArrayList<>();
        ids.add(89L);
        Optional<ParentNode> byId = this.parentRepository.findById(0L);
        byId.ifPresent(e -> {
            System.out.println(e.getName());
            if(e.getSets() != null){
                for (RelationNode set : e.getSets()) {
                    System.out.println(set.getSonNode().getName());
                }
            }
        });
    }

    @Test
    public void testOrder(){
//        Iterable<SonNode> all = this.sonRepository.findAll(Sort.sort(SonNode.class).by(SonNode::getName).ascending());
        Iterable<SonNode> all = this.sonRepository.findAll(Sort.sort(SonNode.class).by(SonNode::getName).descending());
        all.forEach(e -> {
            System.out.println(e.getName());
        });
    }

    @Test
    public void testSelectRelation(){
        Iterable<RelationNode> all = this.relationsNodeRepository.findAll();
        all.forEach(e -> {
            e.setNick("Dad");
            this.relationsNodeRepository.save(e);
            System.out.println(e.getName());
        });
    }



}
