## Nacos + feign 消费者
    feign 请求响应压缩、特殊配置, Okhttp替换httpClient
    okhttp客户端替换默认客户端
        FeignOkHttpConfig.java
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

