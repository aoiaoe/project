# Springboot整合Redis

使用redis实现接口限流
 redis(存储请求次数) + lua(原子性) + aop(接口请求预处理) + 全局异常处理(友好提示)
 
 
 brpop异常问题：
    RedisBrpopComponent.java