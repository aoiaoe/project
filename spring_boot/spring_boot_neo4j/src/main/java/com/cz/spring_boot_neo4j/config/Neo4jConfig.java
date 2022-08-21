package com.cz.spring_boot_neo4j.config;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;

@Configuration
@EnableNeo4jRepositories("com.cz") // 声明neo4j repository存放地址
public class Neo4jConfig {

    @Value("${spring.data.neo4j.uri}")
    private String uri;
    @Value("${spring.data.neo4j.username}")
    private String userName;
    @Value("${spring.data.neo4j.password}")
    private String password;

    @Bean
    public org.neo4j.ogm.config.Configuration getConfiguration() {
        org.neo4j.ogm.config.Configuration configuration =
                new org.neo4j.ogm.config.Configuration.Builder()
                        .uri(uri)
                        .connectionPoolSize(100)
                        .credentials(userName, password)
                        .withBasePackages("com.cz")
                        .build();
        return configuration;
    }

    @Bean
    public SessionFactory sessionFactory() {
        return new SessionFactory(getConfiguration());
    }

    @Bean("transactionManager")
    public Neo4jTransactionManager neo4jTransactionManager(SessionFactory sessionFactory) {
        return new Neo4jTransactionManager(sessionFactory);
    }

    @Bean(name = "session")
    public Session neo4jSession() {
        Driver driver = GraphDatabase.driver(uri, AuthTokens.basic(userName, password));
        return driver.session();
    }
}