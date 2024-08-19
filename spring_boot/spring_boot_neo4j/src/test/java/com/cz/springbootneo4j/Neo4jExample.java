package com.cz.springbootneo4j;

import org.neo4j.driver.*;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Relationship;

import java.util.List;

import static org.neo4j.driver.Values.parameters;

public class Neo4jExample {
    // Neo4j连接信息
    private static final String uri = "bolt://192.168.18.203:27687";
    private static final String user = "neo4j";
    private static final String password = "123456";

    public static void main(String[] args) {
        propertiesJust();
//        nodesAndRels();
    }

    private static void nodesAndRels() {
        // 创建驱动程序
        try (Driver driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password))) {
            // 创建会话
            try (Session session = driver.session()) {
                // 执行Cypher查询
//                String cypherQuery = "MATCH path = (startNode)-[*5]->(endNode) WHERE id(startNode) = $startNodeId RETURN nodes(path), relationships(path)";
                String cypherQuery = "MATCH path = (startNode:Node)-[r:flow*3]->(endNode:Node)\n" +
                        "WHERE startNode.name=\"node1\"\n" +
                        "RETURN nodes(path), relationships(path);";
                cypherQuery = "match path= (m:Movie)-[r:act]->(a:Actor) RETURN nodes(path), relationships(path);";
                cypherQuery = "match path = (c)-[r:Manage] ->(l:Loan)-[rr] ->(ee) where ee.rate is not null return  nodes(path), relationships(path);";
                long startNodeId = 1;  // 替换为实际的起始节点ID
//                Result result = session.run(cypherQuery, parameters("startNodeId", startNodeId));
                Result result = session.run(cypherQuery);

                // 处理查询结果
                while (result.hasNext()) {
                    Record record = result.next();
                    // 获取节点和关系列表
                    List<Node> nodes = record.get("nodes(path)").asList(Value::asNode);
                    List<Relationship> relationships = record.get("relationships(path)").asList(Value::asRelationship);

                    // 处理节点
                    System.out.println("Nodes:");
                    for (Node node : nodes) {
                        System.out.println("Node ID: " + node.id() + ", Labels: " + node.labels() + ", Properties: " + node.asMap());
                    }

                    // 处理关系
                    System.out.println("Relationships:");
                    for (Relationship relationship : relationships) {
                        System.out.println("Relationship ID: " + relationship.id() + ", Type: " + relationship.type() + ", Properties: " + relationship.asMap()
                                + " start: " + relationship.startNodeId() + " end: " + relationship.endNodeId());
                    }
                    System.out.println();
                    System.out.println();
                    System.out.println("-----------------------------");
                }
            }
        }
    }

    private static void propertiesJust() {
        // 创建驱动程序
        try (Driver driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password))) {
            // 创建会话
            try (Session session = driver.session()) {
                // 执行Cypher查询
                String cypherQuery = "match (p:PersonLoan) return p.type limit 5;";
                long startNodeId = 1;  // 替换为实际的起始节点ID
                Result result = session.run(cypherQuery);

                // 处理查询结果
                while (result.hasNext()) {
                    Record record = result.next();
                    // toString()方法会带引号
                    String string = record.get("p.type").asString();
                    System.out.println(string);
                    System.out.println("-----------------------------");
                }
            }
        }
    }
}
