## Nacos + feign 消费者

## Sentinel 流控
    官方文档： https://sentinelguard.io/zh-cn/docs/introduction.html
    本示例参考文档: https://mp.weixin.qq.com/s/Fvdj7aRYLwtzFE8kBSS8Hw
        该文档保存成了图片: sentinel教程.jpeg

### API流控
    此种方式为硬编码方式, 不过可以基于配置中心，监听配置变更，重新加载流控规则
    参考: SentinelConfig.java
    
### Nacos cofig
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