# spring_cloud_consumer_feign_9202

     整合netflix的feign进行服务之间的请求
     使用Ribbon做为http请求存在大量模板代码,feign兼容spring注解@GetMapping,@PostMapping等，可以减少大量的模板代码
     故好的实现为，服务提供者将其请求抽象一层，编写一个接口继承该抽象接口，
     同时标注上@FeignClient注解,即可非常容易的实现一个feign客户端
     请参考feign客户端EntityFeignClient.java
     Feign内部整合了Ribbon,所以可以结合注册中心实现负载均衡,默认理由策略为轮训策略
     所以Feign也可以配置路由策略,参考RibbonRuleConfig.java
     
     整合Feign步骤:
        1、maven依赖:
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-feign</artifactId>
                <version>1.4.7.RELEASE</version>
            </dependency>
        2、配置类加上@EnableFeignClients注解开启Feign客户端
        3、编写Feign客户端
            实现方式1、手动编写接口,方法，方法上用@GetMapping,@PostMapping等注解添加想要访问的url,
                    或者是使用Feign提供的@RequestLine注解
            实现方式2、手动编写接口，继承服务提供者抽象出的一层请求接口
                参考: EntityFeignClient.java
            两种方式都需要在接口上使用@FeignClient注解,标注需要从注册中心发现的服务名
        4、使用:
            参考EntityNormalHttpController.java
     
     
     Feign进阶应用:
        配合Feign的拦截器可以对feign发起的请求进行额外的操作，如添加header等
        编写Feign拦截器: 创建一个类,实现RequestInterceptor接口,并重写apply方法
        参考: FeignInterceptor.java
     
     Feign可以针对某一个服务的客户端进行设置路由策略:
        例如:假设存在三个微服务，提供者:A,B,C 消费者:D
        如果想针对A进行轮训,针对B进行随机路由,针对C进行ZoneAware路由
        则可以像ribbon一样，针对客户端进行配置
        参考:  配置类：RandomFeignRuleConfig.java  RoundRobinRuleConfig.java
              feign客户端: EntityFeignRoundRobinClient.java
        警告： 配置类不能位于@ComponentScan包及其子包下,否则不是针对某个客户端,而会应用于所有客户端
        
      手动创建Feign客户端:
        使用Feign.builder()手动创建客户端
        参考: UserClient.java  FeignFactory.java
     