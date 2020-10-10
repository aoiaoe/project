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
      
      
      
# pom
    <dependency>
        <groupId>net.logstash.logback</groupId>
        <artifactId>logstash-logback-encoder</artifactId>
        <version>5.1</version>
    </dependency>
    
# 配置文件
     

   