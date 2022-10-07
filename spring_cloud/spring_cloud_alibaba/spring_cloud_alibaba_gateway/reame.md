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