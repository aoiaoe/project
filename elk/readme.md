# ELK

    此项目用于整合ElasticSearch + Logstach + Kibana,完成对于系统日志的收集
    系统日志使用springboot默认的logback框架,由logback写到logstach
    logstach将日志搬运存储到elasticsearch,再由kibana可视化展示
    各组件版本均采用6.4.0
    
# 部署elasticsearch
      
      如果是docker启动要配置在启动命令中设置jvm内存,不然容易闪退
      命令: docker run -idt -v D:\docker\es\data:/usr/share/elasticsearch/data -v D:\docker\es\config:/usr/share/elasticsearch/config -v D:\docker\es\plugins:/usr/share/elasticsearch/plugins -p 9200:9200 -p 9300:9300 --name myes -e "cluster.name=elasticsearch" -e "discovery.type=single-node" -e "ES_JAVA_OPTS=-Xms512m -Xmx512m" elasticsearch:6.4.0
      es的配置文件位于classpath: config/es.config中,主要修改elasticsearch.yml中的ip

# 部署kibana
      命令: docker run --name kibana -v  D:\docker\kibana\config:/usr/share/kibana/config -p 5601:5601 -e "elasticsearch.hosts=http://192.168.20.19:9200" -itd kibana:6.4.0   
      kibana的配置文件位于classpath: config/es.config中,主要修改kibana.yml中的ip
      
# 部署logstash
      
      命令:docker run -idt --name=logstash -p 5044:5044 -p 9600:9600 -p 4560:4560 -v G:\docker\logstash\conf:/usr/share/logstash/conf.d -v G:\docker\logstash\logs:/var/log/logstash -v G:\docker\logstash\config:/usr/share/logstash/config logstash:6.4.0
      kibana的配置文件位于classpath: config/logstash.config和conf中,
      logstash.config主要修改logstash.yml中的es地址,以及输入输出的配置文件
      logstash.conf文件夹中保存的是输入输出的配置文件,这里主要修改配置文件中es的地址以及索引名
      
# pom
    <dependency>
        <groupId>net.logstash.logback</groupId>
        <artifactId>logstash-logback-encoder</artifactId>
        <version>5.1</version>
    </dependency>
    <!--若用到了logback的if标签的condition表达式，就需要该坐标-->
    <!--否则会抛出该错误：ERROR in ch.qos.logback.core.joran.conditional.IfAction - Could not find Janino library on the class path. Skipping conditional processing.-->
    <dependency>
        <groupId>org.codehaus.janino</groupId>
        <artifactId>janino</artifactId>
        <version>2.6.1</version>
    </dependency>

    
# 配置文件
     logback的配置文件存在classpath: config/logback-spring.xml
     logback的配置文件中主要需要添加一个发送日志到logstash的appender,如下:
     
      <appender name="logstash"
                   class="net.logstash.logback.appender.LogstashTcpSocketAppender">
         <destination>192.168.0.104:4560</destination>
         <!-- encoder必须配置,有多种可选 -->
         <encoder charset="UTF-8" class="net.logstash.logback.encoder.LogstashEncoder">
             <customFields>{"appname":"mks_dev"}</customFields>
         </encoder>
         <connectionStrategy>
             <roundRobin>
                 <connectionTTL>5 minutes</connectionTTL>
             </roundRobin>
         </connectionStrategy>
     </appender>
     并启用,
     <springProfile name="local">
         <root level="info">
             <appender-ref ref="CONSOLE"/>
             <appender-ref ref="logstash"/>
         </root>
     </springProfile>
     
     还需要在项目配置要yml文件中指定logback配置文件的位置
     logging:
       config: classpath:config/logback-spring.xml
     即可实现将日志从系统(生成日志) -> logstash(收集日志) -> es(存储日志) -> kibana(展示日志)的链路
   