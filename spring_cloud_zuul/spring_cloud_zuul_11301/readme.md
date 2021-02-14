# Getting Started

### 提示
     整合zuul时需要引入引入的依赖包如下
      <dependency>
         <groupId>org.springframework.cloud</groupId>
         <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
      </dependency>
     因为@EnableZuulProxy从2.X版本之后已经移入该依赖包
     此处需要注意的是springboot和springcloud的版本兼容问题，否则项目不能启动
     
     最好是使用maven的DependencyManagement配置进行依赖版本管理，如下：
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Hoxton.SR8</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
    这样就不用去手动去配置各项依赖的版本,不用管理各项依赖包的版本兼容问题
    起步依赖已经做好版本兼容配置,版本依赖如下即可:
     <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
     </dependency>

     <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-consul-discovery</artifactId>
     </dependency>
     
### 路由策略
     第一种:
    zuul:
        servlet-path: #{网关路径,可选}
        routes:
          ${微服务名}:
            path: /${微服务对应访问路径}/**
          ${微服务名}:
            path: /${微服务对应访问路径}/**
     访问公式:http://host:port[/#{网关路径}]/${微服务对应访问路径}/${微服务中接口路径}
     
     示例:
     zuul:
       servlet-path: /api
       routes:
         user:
           path: /user-service/**
         provider:
           path: /prod-service/**
    
     访问http://host:port/api/prod-service/entity/list
     网关根据prod-service这个路径在配置中找到是访问provider微服务
     将会访问到provider微服务中的/entity/list接口