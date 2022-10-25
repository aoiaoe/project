## nacos + config + gateway 
    gateway实时监听nacos配置中心路由配置
    实现动态路由
    参考: DynamicRouteService.java DynamicRouteServiceByNacos.java
    
    小坑: spring cloud gateway没有负载均衡客户端，如果路由定义的url是lb://协议的
     需要手动添加loadbalancer依赖
     
### 自定义过滤器
    1、自定义局部过滤器:
        HeaderTokenGatewayFilterFactory.java
    2、自定义全局过滤器:
        AuthTokenGlobalFilter.java
        场景： 鉴权、日志、限流
    3、自定义body全局缓存过滤器:
        将post请求的body缓存下来，使用请求装饰器类重新包装request，后续过滤器可以再次获取body
        BodyCacheGlobalFilter.java
        // 后续需要在最后一个使用body的过滤器中释放掉数据， 不释放会内存泄漏，释放早了，后续过滤器获取不到数据
    
### 自定义全局异常
    处理全局异常
    使用场景: 将所有异常以json的方式对客户端友好提示
    参考: JsonCustomizeErrorHandler.java

### 集成sleuth实现日志链路追踪
    sleuth非倾入性，使用ThreadLocal保存上下文，而spring cloud gateway由于使用异步框架，
    一次请求会使用不同的线程进行处理，所以不能使用ThreadLocal获取上下文打印traceId和spanId
    
    解决方案：
        1：修改配置
        spring:
            sleuth:
                reactor:
                    #在2020.0.0中，已将默认网关反应器检测模式更改为manual,
                    #ON_EACH-用跟踪表示形式包装每个Reactor运算符。在大多数情况下，传递跟踪上下文。此模式可能会导致性能急剧下降。
                    #ON_LAST-将最后一个Reactor运算符包装在跟踪表示中。在某些情况下传递跟踪上下文，
                    #因此访问MDC上下文可能不起作用。此模式可能会导致中等性能下降。
                    #MANUAL-以最小侵入性的方式包装每个Reactor，而无需通过跟踪上下文。由用户决定
                    instrumentation-type: decorate_on_each
        2、改进编程规范
            参考：
            文章：https://blog.csdn.net/zhxdick/article/details/120458875?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522166668082316782428656973%2522%252C%2522scm%2522%253A%252220140713.130102334.pc%255Fblog.%2522%257D&request_id=166668082316782428656973&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~blog~first_rank_ecpm_v1~rank_v31_ecpm-12-120458875-null-null.nonecase&utm_term=gateway&spm=1018.2226.3001.4450
            图片：Spring Cloud Gateway 没有链路信息(上).jpg
            文章：https://blog.csdn.net/zhxdick/article/details/120473652?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522166668082316782428656973%2522%252C%2522scm%2522%253A%252220140713.130102334.pc%255Fblog.%2522%257D&request_id=166668082316782428656973&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~blog~first_rank_ecpm_v1~rank_v31_ecpm-4-120473652-null-null.nonecase&utm_term=gateway&spm=1018.2226.3001.4450
            图片：Spring Cloud Gateway 没有链路信息(中).jpg
            文章：https://zhanghaoxin.blog.csdn.net/article/details/120496810?spm=1001.2101.3001.6661.1&utm_medium=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1-120496810-blog-125175718.pc_relevant_default&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1-120496810-blog-125175718.pc_relevant_default&utm_relevant_index=1
            图片：Spring Cloud Gateway 没有链路信息(下).jpg

## gateway整合sentinel实现限流
    系统应该在网关层面进行限流，不应该让巨量流量流向网关后面的微服务中
    
    依赖:
        <!-- 网关限流 -->
        <!-- https://mvnrepository.com/artifact/com.alibaba.cloud/spring-cloud-starter-alibaba-sentinel -->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
        </dependency>
        <!-- https://mvnrepository.com/artifact/com.alibaba.cloud/spring-cloud-alibaba-sentinel-gateway -->
        <!-- gateway整合sentinel依赖 -->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-alibaba-sentinel-gateway</artifactId>
            <version>2021.1</version>
        </dependency>
    增加配置:
        spring:
          cloud:
            sentinel:
              transport:
                dashboard: localhost:9999
              eager: true # 启动即注册限流规则到sentinel，否则会延迟到请求之后才会注册到sentinel
    修改sentinel dashborad启动命令:
            java -jar -Dserver.port=9999 -Dcsp.sentinel.app.type=1 sentinel-dashboard-1.8.5.jar
            增加: -Dcsp.sentinel.app.type=1 代表注册的应用是网关类型
    
    硬编码:
        方式1: 代码限流配置:
            参考: MySentinelGatewayConfiguration.java
        方式2: 文件配置:
            1、增加依赖:
                <dependency>
                    <groupId>com.alibaba.csp</groupId>
                    <artifactId>sentinel-datasource-nacos</artifactId>
                    <version>1.8.3</version>
                </dependency>
            2、增加配置:
                spring:
                  cloud:
                      # 设置sentinel数据源
                      datasource:
                        # 文件保存限流配置
                        ds1.file:
                          file: classpath:flowrule/flow-rule.json
                          ruleType: gw-flow # 限流规则
                        ds2.file:
                          file: classpath:flowrule/flow-rule-api.json
                          ruleType: gw-api-group # api分组
             3、增加两个json文件:
                文件1(限流规则文件): flow-rule.json    
                [
                  {
                    "resource": "userPrefix",
                    "resourceMode": 0,
                    "count": 2,
                    "intervalSec": 10
                  }
                ]
                文件2(api分组配置)： flow-rule-api.json   
                [
                  {
                    "apiName": "userPrefix",
                    "predicateItems": [
                      {
                        "pattern": "/gw/user/user/**",
                        "matchStrategy": 1
                      }
                    ]
                  }
                ]
    
    
    小坑: 应用集成sentinel之后会通过类  SimpleHttpHeartbeatSender 定时向dashboard发送心跳，
         心跳包中会带着应用类型(csp.sentinel.app.type).  如果是网关类型，dashboard中会增加一个【API管理】菜单
         虽然在上面【 gateway整合sentinel依赖】的依赖包中，SentinelSCGAutoConfiguration 类中initAppType()方法会增加系统变量(csp.sentinel.app.type=11)
         但是，此类自动注入的时机 后于 SimpleHttpHeartbeatSender类的初始化, 导致 SimpleHttpHeartbeatSender发送心跳包中应用类型(csp.sentinel.app.type=0)
         从而dashboard中的【API管理】菜单显示不出来
         解决方案:
            启动类中main方法第一行添加 System.setProperty(SentinelConfig.APP_TYPE_PROP_KEY, "1"); 将此值写入到系统变量， SimpleHttpHeartbeatSender初始化时就可以获取到应用类型



## Sentinel Dashboard展示应用限流规则、修改限流规则下发到应用的原理
    以下结论由自己抓包后,结合sentinel客户端源代码下的结论，不一定正确，也未查看sentinel dashboard工程源码做分析，以后有机会再看dashboard源码验证
    wireshark抓本地包方法: https://blog.csdn.net/myyllove/article/details/113879198
    
    如果在配置文件中，配置了spring.cloud.sentinel.eager=true, 则应用启动之后，
    自动装配类 SentinelAutoConfiguration 的init()方法最后会去用spi初始化 SimpleHttpHeartbeatSender 用于向dashboard发送心跳包
    心跳包里面会带上一个spring.cloud.sentinel.transport.port(默认8719)指定的一个serverSocket的端口, 并启动一个serverSocket, 用于接收dashboard的请求
    应用会定时向dashboard发送心跳，维持状态
    然后dashboard就会向serverSocket发送一系列的请求， 
        比如/metrix                          (应用状态)
        /gateway/getApiDefinition           (获取api规则用于页面展示)
        /gateway/updateApiDefinition        (在dashboard修改限流规则之后，推送给应用)
        ....
    
    所以说dashboard对于应用是使用pull方式进行管理展示





