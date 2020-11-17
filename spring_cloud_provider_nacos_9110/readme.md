#
    如果想要使用nacos的注册中心功能
    需要引入依赖包
    <!--nacos-config的Spring cloud依赖  -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
        <version>0.9.0.RELEASE</version>
    </dependency>
            
    如果想要使用nacos的配置中心功能
    需要引入依赖包
    <!--nacos-config的Spring cloud依赖  -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
        <version>0.9.0.RELEASE</version>
    </dependency>
    否则配置中心不生效