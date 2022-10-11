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
    