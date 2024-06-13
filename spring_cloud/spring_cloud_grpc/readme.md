Spring Cloud + gRpc + Nacos注册中心 整合

## gRpc服务端依赖
    <dependency>
        <groupId>net.devh</groupId>
        <artifactId>grpc-server-spring-boot-starter</artifactId>
        <version>2.0.1.RELEAS</version>
    </dependency>

## gRpc客户端依赖
    <dependency>
        <groupId>net.devh</groupId>
        <artifactId>grpc-client-spring-boot-starter</artifactId>
        <version>2.0.1.RELEAS</version>
    </dependency>

## proto文件编译插件
    

## gRpc客户端 + Nacos注册中心 实现服务发现
### gRpc服务端暴露自己的rpc服务端口到元信息中
    spring:
        cloud:
            nacos:
                discovery:
                    enabled: true
                        server-addr: localhost:8848
                    namespace: dev
                    ## 需要将gRpc的端口暴露到nacos的元数据中, 客户端才能对gRpc端口发起远程调用
                    metadata:
                        gRPC:
                        port: ${grpc.server.port}

### gRpc客户端
    使用@GrpcClient("nacos-grpc-demo")注解，其中 nacos-grpc-demo 为服务端注册到注册中心的服务名
    在服务启动的时候，会使用注入的DiscoveryClient去注册中心获取对应服务名的服务，然后获取元信息中的gRpc端口，组成服务端地址
    
    大概流程:
    1、客户端在Service中注入gRpc的Channel对象
        @Service
        public class MyGreeterGrpcClient {

            @GrpcClient("nacos-grpc-demo")
            public Channel channel;
        }
    2、gRpc的自动装配类 GrpcClientAutoConfiguration.java 会注入一个 GrpcClientBeanPostProcessor.java 对象
    该bean会对Service中的Channel对象进行一个实例化并注入
    GrpcClientBeanPostProcessor.java # postProcessAfterInitialization() :
        Channel channel = channelFactory.createChannel(annotation.value(), list);
    DiscoveryClientChannelFactory.java # createChannel() :
        Channel channel = builder.build();
    AbstractManagedChannelImplBuilder.java # build() :
         return new ManagedChannelOrphanWrapper(new ManagedChannelImpl()
    ManagedChannelImpl.java # 构造器 :
        this.nameResolver = getNameResolver(target, nameResolverFactory, nameResolverParams);
    ManagedChannelImpl.java # getNameResolver() :
        NameResolver resolver = nameResolverFactory.newNameResolver(targetUri, nameResolverParams);
    DiscoveryClientResolverFactory.java # newNameResolver()
        此方法将服务发现客户端注入的nameResolver中
    3、当第一次触发远程调用时
        会进入 DiscoveryClientNameResolver.java # refresh(), 最终调用 resolve()方法
        resolve()方法中,会使用服务发现客户端发现客户端配置的服务名
        如果获取到了服务，则查询其元信息中的 gRPC.port 属性,获取其rpc的端口号
        所以: 服务端的元信息中一定要存在该key
    4、后续每30秒钟，NacosWatch.java # nacosServicesWatch()会发送一次HeartBeatEvent事件
        DiscoveryClientChannelFactory.java # heartbeat()方法对其进行了监听
        然后会使用 DiscoveryClientNameResolver.java # resolve() 方法进行服务信息更新
        从而达到定时更新本地远程服务信息的效果

## 编写proto协议文件并编译
    
    1、编写proto文件 Users.proto
        syntax = "proto3";

        option java_package = "com.cz.grpc";
        
        service Users {
            rpc GetUserInfo (GetUserInfoRequest) returns (GetUserInfoResponse) {}
        }
        
        message GetUserInfoRequest {
            int64 id = 1;
        }
        
        message GetUserInfoResponse {
            int64 id = 1;
            string name = 2;
            string sex = 3;
            int32 age = 4;
        }
    2、安装maven插件
        <build>
            <extensions>
                <extension>
                    <groupId>kr.motd.maven</groupId>
                    <artifactId>os-maven-plugin</artifactId>
                    <version>${os.plugin.version}</version>
                </extension>
            </extensions>
    
            <plugins>
                <plugin>
                    <groupId>org.xolstice.maven.plugins</groupId>
                    <artifactId>protobuf-maven-plugin</artifactId>
                    <version>${protobuf.plugin.version}</version>
                    <extensions>true</extensions>
                    <configuration>
                        <protocArtifact>com.google.protobuf:protoc:${protoc.version}:exe:${os.detected.classifier}</protocArtifact>
                        <pluginArtifact>io.grpc:protoc-gen-grpc-java:${grpc.version}:exe:${os.detected.classifier}</pluginArtifact>
                        <pluginId>grpc-java</pluginId>
                        <!--默认值-->
                        <protoSourceRoot>${project.basedir}/src/main/proto</protoSourceRoot>
                        <!--默认值-->
                        <!--<outputDirectory>${project.build.directory}/generated-sources/protobuf/java</outputDirectory>-->
                        <outputDirectory>${project.basedir}/src/main/java</outputDirectory>
                        <!--设置是否在生成java文件之前清空outputDirectory的文件，默认值为true，设置为false时也会覆盖同名文件-->
                        <clearOutputDirectory>false</clearOutputDirectory>
                        <!--更多配置信息可以查看https://www.xolstice.org/protobuf-maven-plugin/compile-mojo.html-->
                    </configuration>
                    <executions>
                        <execution>
                            <!--在执行mvn compile的时候会执行以下操作-->
                            <phase>compile</phase>
                            <goals>
                                <!--生成OuterClass类-->
                                <goal>compile</goal>
                                <!--生成Grpc类-->
                                <goal>compile-custom</goal>
                            </goals>
                        </execution>
                    </executions>
                </plugin>
            </plugins>
        </build>
    3、点击compile编译项目，会将指定文件夹下的proto文件编译成Protobuf协议的文件
        参考 grpc-lib模块
        