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
### @Import注解
    com.cz.spring_boot_mix.importannotation
### Spring容器初始化过程中，bean的扩展点
    com.cz.spring_boot_mix.extention
        
###  guava:
    事件总线（同步、异步） com.cz.spring_boot_mix.guava.eventbus
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
#### 自定义参数解析器
    com.cz.spring_boot_mix.paramresolver