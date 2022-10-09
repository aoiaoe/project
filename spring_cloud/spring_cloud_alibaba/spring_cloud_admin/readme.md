## Springboot admin监控服务端
    通过springboot admin可以对应用进行监控，获取应用运行的实时状态
    
## 步骤
    1、添加依赖
        <!-- 需要注意此依赖的版本 需要和springboot配套 否则1、应用无法启动 2、应用启动之后服务无法访问 -->
        <!-- https://mvnrepository.com/artifact/de.codecentric/spring-boot-admin-starter-server -->
        <dependency>
            <groupId>de.codecentric</groupId>
            <artifactId>spring-boot-admin-starter-server</artifactId>
            <version>2.4.4</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    
    2、启动类上增加 @EnableAdminServer 注解即可

## Springboot admin server实现监控的两种方式
    1、被监控和管理的应用，使用Springboot admin client库， 通过http调用注册到springboot admin server上
    2、Springboot Admin server注册到注册中心上，从注册中心获取被监控和管理的应用程序实现监控
    
    注意： 被监控的服务需要暴露actuator的端点才能实现监控, 
    如果有server.servlet.context-path则需要将该值设置给actuator
    # 例如
    management:
      server:
        base-path: ${server.servlet.context-path}
    # 如果是通过注册中心的方式进行监控，则需要在nacos服务发现的配置中增加端点的上下文路径
    # 例如
    server:
      servlet:
        context-path: /admin
    spring:
      cloud:
        nacos:
          discovery:
            metadata:
              management:
                context-path: ${server.servlet.context-path}/actuator