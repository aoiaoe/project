# spring_cloud_consumer_ribbon_92xx

     服务消费者：
        注册中心: consul
        负载均衡: ribbon

# ribbon

##### what's ribbon?

     Spring Cloud Ribbon是基于Netflix Ribbon实现的一套客户端       负载均衡的工具。
      
     简单的说，Ribbon是Netflix发布的开源项目，主要功能是提供客户端的软件负载均衡算法，将Netflix的中间层服务连接在一起。
     Ribbon客户端组件提供一系列完善的配置项如连接超时，重试等。
     简单的说，就是在配置文件中列出Load Balancer（简称LB）后面所有的机器，Ribbon会自动的帮助你基于某种规则（如简单轮询，随机连接等）去连接这些机器。
     我们也很容易使用Ribbon实现自定义的负载均衡算法。

##### what can ribbon do?

     LB，即负载均衡(Load Balance)，在微服务或分布式集群中经常用的一种应用。
     负载均衡简单的说就是将用户的请求平摊的分配到多个服务上，从而达到系统的HA。
     常见的负载均衡有软件Nginx，LVS，硬件 F5等。
     相应的在中间件，例如：dubbo和SpringCloud中均给我们提供了负载均衡，SpringCloud的负载均衡算法可以自定义。 

# 整合步骤

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
      5、ribbon默认服务路由策略为RoundRobinRule轮训策略
        修改ribbon默认策略参考RibbonRuleConfig
        
      6、测试:
        参考controller包下的类:
            EntityDiscoveryClientController.java
            EntityNormalHttpController.java
            EntityRibbonController.java
       
      PS :
        ribbon的默认路由为RoundRoubinRule
        我们可以配置为其他的路由策略,如:
            RoundRobinRule: 轮训策略
            RandomRule: 随机路由策略
            RetryRule: 重试策略,在规定的时间段内未选择服务成功,则使用subRule(即RoundRobinRule)进行服务路由
            WeightedResponseTimeRule: 基于响应权重的策略,后台线程定时从服务的状态里面评价其相应时间，为每一个服务计算出一个权重,
                然后产生一个0到所有权重和的随机数,再确定所属权重的索引，从而确定选择某个服务
                当刚开始运行时尚无权重，则使用父类(RoundRobin)的路由策略
            BestAvailableRule: 逐个考察server，过滤掉tripped的server，再选择其中ActiveRequestsCount(活动的请求连接数)最小的一个server
            AvailabilityFilteringRule: 使用一个AvailabilityPredicate过滤条件来过滤server,其实就是检查ServerStatus中的各服务的运行状态
            ZoneAvoidanceRule: 基于server所在区域性能和server可用性选择的策略,使用ZoneAvoidancePredicate和AvailabilityPredicate来判断是否选择某个server,
                 前一个判断一个zone的运行性能是否可用,剔除不可用的zone里的所有sever，后一个用于过滤掉连接数过多的server

# 自定义路由策略:

       参考MyRibbonRoutRule.java,主要继承AbstractLoadBalancerRule类并重写choose方法
       此时可以配置针对全局微服务生效,或者针对某一个/些生效
       1、如果想针对全局微服务生效,则将自定义路由策略注册到容器中即可,参考RibbonRuleConfig.java
       2、如果想针对某一个微服务生效,可以使用@RibbonClient注解
            示例:@RibbonClient(value = "provider", configuration = RibbonRuleConfig.class)
       3、如果想针对某一些微服务生效,可以使用@RibbonCliebts注解
        
       警告：@RibbonClient注解配置类不能放在@ComponentScan扫描的包及其子包下面,否则我们自定义的配置类会被所有的ribbon客户端共享
            就达不到特殊化定制的目的了
            示例: @RibbonClient(name = "provider", configuration = RibbonRuleCustomizer.class)
            参考RibbonRuleCustomizer.java,必须分包存放该路由配置类
      