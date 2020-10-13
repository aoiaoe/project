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
                