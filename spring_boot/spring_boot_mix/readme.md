## 杂项

### AOP执行顺序
#### 创建aop的另一种方式
    com.cz.spring_boot_mix.aop.custom
### 循环依赖
    源代码跟踪测试： com.cz.spring_boot_mix.circulardependency.CircularDependencyTest
        
### 定时任务与线程池
    1、Async定时任务
    2、可动态修改执行时间的SchedulingConfigurer定时任务， CronSchedulingConfig.java
    测试时 需要打开启动类注解

### spring事件监听
    com.cz.spring_boot_mix.event

### @Import注解
    com.cz.spring_boot_mix.importannotation

### Spring容器初始化过程中，bean的扩展点
    com.cz.spring_boot_mix.extention
        
###  guava:
    事件总线（同步、异步） com.cz.spring_boot_mix.guava.eventbus
    缓存 com.cz.spring_boot_mix.guava.cache
### sentinel:
    代码配置sentinel限流 SentinelConfig.java
### 环境变量读取
    com.cz.spring_boot_mix.environment
### JSON 2 POJO with SPEL
    使用spel将json数据转换为bean, 
    使用场景为: 忘记了
    com.cz.spring_boot_mix.beanPropertiesMap
### spring 事件
    com.cz.spring_boot_mix.event
    
### 泛型
    com.cz.spring_boot_mix.genericTypeResolver

### 自定义参数解析器
    com.cz.spring_boot_mix.paramresolver

### java代码调用http上传文件接口 
    com.cz.spring_boot_mix.upload
    
### SQL解析
    com.cz.spring_boot_mix.sqlparse

### Properties属性类注入
    使用@EnableConfigurationProperties注解，可以将属性类注入到容器
    参考: com.cz.spring_boot_mix.config.AnnoConfig.java
         com.cz.spring_boot_mix.config.MyConfigProperties.java

### 定时任务
    com.cz.spring_boot_mix.scheduled

### websocket
    com.cz.spring_boot_mix.websocket

### restTemplate调用上传文件接口
    com.cz.spring_boot_mix.upload

### Actuator监控
    com.cz.spring_boot_mix.actuator
    实现方式:
        1、实现 HealthIndicator 接口, 参考: OutServiceIndicator.java
        2、实现 CompositeHealthContributor 接口, 参考: ThreadPoolContributor.java
    yml配置：
        management:
            server:
                port: 12345   # 单独指定actuator端口
            endpoints:
                web:
                    base-path: /admin  # actuator的根上下文路径
                    exposure:
                        include: '*'
            endpoint:
                health:
                    show-details: always  # actuator始终展示各个组件的状态, 自定义外部组件的服务状态也可以暴露出去,OutServiceIndicator.java