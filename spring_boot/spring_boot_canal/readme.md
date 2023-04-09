## Springboot整合canal实现数据库数据同步

### 从github下载最新deploy发行版
    解压，进入conf目录，修改canal.properties和instance.properties
    然后执行bin目录下的start.sh脚本启动canal服务端
    
### 使用TCP直连方式
    canal.properties和instance.properties内容参考： classpath:/tcp
    启动canal之后，运行客户端代码：CanalClient.java
    使用tcp连接canal并读取同步数据
    
### RabbitMq
    canal.properties和instance.properties内容参考： classpath:/rabbit
    
    
## 踩坑
    1、账号需要REPLICATION CLIENT权限，授权之后，需要flush privileges刷新权限
    2、MYSQL必须开启BINGLOG才能进行同步，否则canal无法连接mysql
    
## 参考
    1.开启binlog
        https://www.cnblogs.com/1285026182YUAN/p/16193399.html
    2、整合canal
        https://mp.weixin.qq.com/s/yZlB56oRpde518vXCumocA (整合canal.jpeg)
        