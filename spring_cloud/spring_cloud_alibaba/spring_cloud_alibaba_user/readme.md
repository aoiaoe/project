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
