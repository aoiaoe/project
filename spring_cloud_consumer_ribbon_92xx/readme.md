# spring_cloud_consumer_ribbon_92xx
     服务消费者：
        注册中心: consul
        负载均衡: ribbon
      
    步骤:
    1、maven依赖:
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-consul-discovery</artifactId>
        </dependency>  
        <!-- 用于服务健康检查 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <!-- 负载均衡客户端 -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
        </dependency>
    2、配置类上加上@EnableDiscoveryClient
    3、yml文件里面添加:
        spring:
          application:
            name: consumer
          cloud:
            consul:
              host: 127.0.0.1
              port: 8500
              enabled: true # 是否启用consul
              discovery:
                register: true # 是否注册到consul
                enabled: true # 是否启用服务发现
                prefer-ip-address: true # 注册到consul是否使用ip
                heartbeat: # 心跳
                  enabled: true
                  ttl-value: 30
                  ttl-unit: s
                instance-id: ${spring.application.name}-${spring.cloud.client.ip-address}-${server.port}
      4、将RestTemplate注册到容器
        参考配置类: RestTemplateConfig.java
        
      5、测试:
        参考controller包下的类:
            EntityDiscoveryClientController.java
            EntityNormalHttpController.java
            EntityRibbonController.java
                