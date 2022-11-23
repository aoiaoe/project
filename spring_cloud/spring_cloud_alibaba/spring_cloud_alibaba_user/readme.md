## Nacos + feign 消费者
    feign 请求响应压缩、特殊配置, Okhttp替换httpClient
    okhttp客户端替换默认客户端
    需要先修改配置：
    feign:
      # 底层切换为OkHttp
      httpclient:
        enabled: false
      okhttp:
        enabled: true
        参考：FeignOkHttpConfig.java
#### feign原理
    1、@EnabeleFeignClients 注解通过@Import引 入FeignClientsRegistrar，FeignClientsRegistrar中扫描
        @EnabeleFeignClients注解中配置的路径和类，将@FeignClient注解的类注册为 FeignClientFactoryBean
    2、当业务类需要注入Feign客户端时，Spring会通过查找到 FeignClientFactoryBean 的bean，
        然后通过调用链 getObject() -> getTarget() -> loadBalance() -> target.target() -> 
        feign.target(target) -> build().newInstance(target);
        最终会回到Feign的内部类Builder中对feign客户端进行创建动态代理
    参考： 文章：https://blog.csdn.net/HRebel/article/details/111239127?spm=1001.2101.3001.6661.1&utm_medium=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1-111239127-blog-124356745.pc_relevant_3mothn_strategy_recovery&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1-111239127-blog-124356745.pc_relevant_3mothn_strategy_recovery&utm_relevant_index=1
          图片： SpringCloud之OpenFegin基本介绍及原理分析.jpg
          
#### feign客户端名称的小坑
    [@FeignClient] 注解的contextId最好配置且唯一，否则如果多个FeignClient使用相同的value或者name，注入时会报错
    原因：
     FeignClientsRegistrar.registerBeanDefinitions() -> registerFeignClients() ->
     String name = getClientName(attributes);方法中获取feignClient的名称优先级
         是contextId > value > name > serviceId
     所以如果不同FeignClient使用相同的value则会因为有相同的beanName无法注入
    
    解决方案：
     1、增加配置，spring.main.allow-bean-definition-overriding: true  允许同名bean覆盖
         但是不符合常理，既然你定义了FeignClient就是要使用的，那么覆盖了就没有意义了，删除该类或者合并同名FeignClient即可
     2、通过设置@FeignClient注解的注contextId属性，且每一个值全局唯一即可
         注入的时候则需要注意根据名称进行注入
        
## loadbalancer
    原理：
    1、restTemplate
        loadbalancer自动配置时，会实例化一个RetryLoadBalancerInterceptor拦截器，
        然后通过RestTemplateCustomizer定制化器去将拦截器给配置到restTemplate客户端中
        故之后通过restTemplate发起http请求则会使用该拦截器进行负载均衡
    2、feign
        通过FeignLoadBalancerAutoConfiguration自动装配类，根据classpath中的的http客户端，调用相应的配置类，
        如此项目中因为引入了okhttp客户端，所以使用OkHttpFeignLoadBalancerConfiguration类进行feign客户端的装配
        但是此处需注意，因为需要使用okhttp客户端，所以真正的feign客户端是在FeignOkHttpConfig类中进行实例化的
        
    
## Sentinel 流控
    官方文档： https://sentinelguard.io/zh-cn/docs/introduction.html
    本示例参考文档: https://mp.weixin.qq.com/s/Fvdj7aRYLwtzFE8kBSS8Hw
        该文档保存成了图片: sentinel教程.jpeg

### API流控
    此种方式为硬编码方式, 不过可以基于配置中心，监听配置变更，重新加载流控规则
    参考: SentinelConfig.java
    
### sentinel动态流控配置
    sentinel支持多种外部数据源存储流控配置，例如:nacos, zk, apollo, redis, consul
    本项目中使用nacos作为外部存储， 原理为启动一个NacosConfigServer监听nacos服务器中指定dataId的变化
    当项目启动时，会获取一次配置用于初始化流控配置，之后使用监听器进行配置变化监听，近实时修改流控规则
    
    （与之相同的操作还有 spring_cloud_alibaba_gateway 模块中的网关获取ncos中配置实现动态路由配置， 
    不过网关中的代码需要自己实现， sentinel官方已经实现） 
    
    操作步骤：
        1、引入依赖
            <!-- https://mvnrepository.com/artifact/com.alibaba.csp/sentinel-datasource-nacos -->
            <dependency>
                <groupId>com.alibaba.csp</groupId>
                <artifactId>sentinel-datasource-nacos</artifactId>
                <version>1.8.3</version>
            </dependency>
        2、增加数据源配置
            spring:
              cloud:
                sentinel:
                  # sentinel数据源， 获取外部存储的限流规则
                  datasource:
                    ds:
                      nacos:
                        serverAddr: ${spring.cloud.nacos.discovery.server-addr}
                        username: nacos
                        password: nacos
                        namespace: dev
                        ruleType: flow
                        dataId: nacos-config-sentinel-user-sentinel.json
                        data-type: json
         3、在nacos中创建一个dataId和上面配置相同的配置文件, 类型要和data-type相同
            例如根据上面配置创建一个dataId为nacos-config-sentinel-user-sentinel.json的json类型的文件
            内容为：
            [
            	{
            	  "resource": "anno",        // 代码中埋点的资源或者url
            	  "limitApp": "default",     // 限制的来源， 基本上默认default
            	  "grade": 1,                // 阈值类型 1：QPS  0：线程数
            	  "count": 121,              // 限制阈值
            	  "strategy": 0,             // 流控模式 0：直接 1：关联(当关联的链路到达阈值时，限流自己) 2：链路
            	  "controlBehavior": 0,      // 流控效果 0：直接拒绝 1：热启动 2：排队等待
            	  "clusterMode": false       // 是否集群
            	}
            ]
              

### Nacos config
    nacos配置中心支持 扩展配置，共享配置
    但是此处有一个关于yaml格式的坑：
    就是如果项目配置是以yaml格式编写，则在nacos中配置的dataid必须以.yml结尾，
    并且在下面的配置中也要带上.yml后缀，否则不能进行配置
    原因在于：如果nacos中配置没有设置结尾，则默认是.properties，
    当nacos客户端对此dataid获取到配置中心的配置之后，则会把yml格式的内容当做是属性文件，
    从而将每一行当做是一个配置，导致配置失败
    spring
      cloud:
        nacos:
          config:
            enabled: true
            server-addr: http://tx-gd:8948
            username: nacos
            password: nacos
            file-extension: yml
            group: DEFAULT_GROUP
            namespace: dev
            # 主配置 > 扩展配置(extension-configs) > 共享配置(shared-configs)
            # 扩展配置
            extension-configs[0]:
              dataId: nacos_config_sentinel_consumer_ext.yml
            # 共享配置
            shared-configs[0]:
              dataId: common.yml
## 配置更新
    @ConfigurationProperties注解的类会自动刷新, 但是如果增加了@RefreshScope注解则是延迟更新，
        在使用时才会重新初始化属性, 参考 UserConfig.java
    @Value注解的属性不会自动刷新，需要配合@RefreshScope  
     
## 配置更新之后触发操作
    目前可以通过三种方式实现
    
    1、通过重写set方法，在自动初始化bean注入rules的时候完成grayRuleInfos的初始化（不够友好）。
    
    2、通过EventListener监听下发的配置修改事件，然后修改对应的grayRuleInfos初始化（获取到的是上一次rules的值）
    
    @EventListener
    
    public void envChangeListener(EnvironmentChangeEvent event) {}
      
    3、通过@PostConstruct，比较优雅
    
    每次配置变更都是不是销毁原来的bean，而是重新将bean初始化
    如果加了@RefreshScope，则会延迟加载，只有在使用的时候才会触发PostConstruct对应的操作
    
    作者：zornil
    链接：https://www.jianshu.com/p/b9945db84c4e
    来源：简书
    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。


## ribbon原理 
    参考
        文章：https://blog.csdn.net/HRebel/article/details/110760970?spm=1001.2014.3001.5502
        图片：SpringCloud之Ribbon客户端负载原理分析（一）-LoadBalancerInterceptor原理分析.jpg
        文章：https://blog.csdn.net/HRebel/article/details/110870816?spm=1001.2014.3001.5502
        图片：SpringCloud之Ribbon客户端负载原理分析（二）-RibbonLoadBalancerClient原理分析.jpg
        文章：https://blog.csdn.net/HRebel/article/details/110941364?spm=1001.2014.3001.5502
        图片：SpringCloud之Ribbon客户端负载原理分析（三）-ILoadBalancer原理分析.png
        



### Spring cloud stream
    简单使用参考：
        com.cz.spring_cloud_alibaba.service.stream
        
### SkyWalking性能监控
    参考：https://blog.csdn.net/qq924862077/article/details/89409746
            整合SkyWalking.png
    步骤：
        1、下载SkyWalking, 链接：https://archive.apache.org/dist/skywalking/
        2、解压，前端服务端口8080，如需修改，进入webapp修改webapp.yml中的配置
        3、对于单机使用或者开发学习，可以使用服务默认存储H2，
            进入bin目录，使用startup.sh脚本，启动项目
        4、启动之后可以通过webapp中的端口进行页面访问
        5、拷贝压缩包中的agent文件夹到需要使用javaagent的项目中，或者本地，
            获取到agent文件夹中的skywalking-agent.jar文件的路径
        6、在需要使用javaagent的项目中，增加JVM启动参数
            -javaagent:/Users/sephiroth/study/IdeaProjects/project/spring_cloud/spring_cloud_alibaba/spring_cloud_alibaba_user/agent/skywalking-agent.jar
            -Dskywalking.agent.service_name=spring_cloud_sentinel_order
            -Dskywalking.collector.backend_service=tx-gd:11800
            
            其中spring_cloud_sentinel_order是需要显示在SkyWalking中的服务名称
            tx-gd:11800是指标提交的SkyWalking的服务器地址
            11800端口是SkyWalking服务启动之后，自动启动的端口
            
    