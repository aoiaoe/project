Spring Cloud + gRpc + Nacosע������ ����

## gRpc���������
    <dependency>
        <groupId>net.devh</groupId>
        <artifactId>grpc-server-spring-boot-starter</artifactId>
        <version>2.0.1.RELEAS</version>
    </dependency>

## gRpc�ͻ�������
    <dependency>
        <groupId>net.devh</groupId>
        <artifactId>grpc-client-spring-boot-starter</artifactId>
        <version>2.0.1.RELEAS</version>
    </dependency>

## proto�ļ�������
    

## gRpc�ͻ��� + Nacosע������ ʵ�ַ�����
### gRpc����˱�¶�Լ���rpc����˿ڵ�Ԫ��Ϣ��
    spring:
        cloud:
            nacos:
                discovery:
                    enabled: true
                        server-addr: localhost:8848
                    namespace: dev
                    ## ��Ҫ��gRpc�Ķ˿ڱ�¶��nacos��Ԫ������, �ͻ��˲��ܶ�gRpc�˿ڷ���Զ�̵���
                    metadata:
                        gRPC:
                        port: ${grpc.server.port}

### gRpc�ͻ���
    ʹ��@GrpcClient("nacos-grpc-demo")ע�⣬���� nacos-grpc-demo Ϊ�����ע�ᵽע�����ĵķ�����
    �ڷ���������ʱ�򣬻�ʹ��ע���DiscoveryClientȥע�����Ļ�ȡ��Ӧ�������ķ���Ȼ���ȡԪ��Ϣ�е�gRpc�˿ڣ���ɷ���˵�ַ
    
    �������:
    1���ͻ�����Service��ע��gRpc��Channel����
        @Service
        public class MyGreeterGrpcClient {

            @GrpcClient("nacos-grpc-demo")
            public Channel channel;
        }
    2��gRpc���Զ�װ���� GrpcClientAutoConfiguration.java ��ע��һ�� GrpcClientBeanPostProcessor.java ����
    ��bean���Service�е�Channel�������һ��ʵ������ע��
    GrpcClientBeanPostProcessor.java # postProcessAfterInitialization() :
        Channel channel = channelFactory.createChannel(annotation.value(), list);
    DiscoveryClientChannelFactory.java # createChannel() :
        Channel channel = builder.build();
    AbstractManagedChannelImplBuilder.java # build() :
         return new ManagedChannelOrphanWrapper(new ManagedChannelImpl()
    ManagedChannelImpl.java # ������ :
        this.nameResolver = getNameResolver(target, nameResolverFactory, nameResolverParams);
    ManagedChannelImpl.java # getNameResolver() :
        NameResolver resolver = nameResolverFactory.newNameResolver(targetUri, nameResolverParams);
    DiscoveryClientResolverFactory.java # newNameResolver()
        �˷����������ֿͻ���ע���nameResolver��
    3������һ�δ���Զ�̵���ʱ
        ����� DiscoveryClientNameResolver.java # refresh(), ���յ��� resolve()����
        resolve()������,��ʹ�÷����ֿͻ��˷��ֿͻ������õķ�����
        �����ȡ���˷������ѯ��Ԫ��Ϣ�е� gRPC.port ����,��ȡ��rpc�Ķ˿ں�
        ����: ����˵�Ԫ��Ϣ��һ��Ҫ���ڸ�key
    4������ÿ30���ӣ�NacosWatch.java # nacosServicesWatch()�ᷢ��һ��HeartBeatEvent�¼�
        DiscoveryClientChannelFactory.java # heartbeat()������������˼���
        Ȼ���ʹ�� DiscoveryClientNameResolver.java # resolve() �������з�����Ϣ����
        �Ӷ��ﵽ��ʱ���±���Զ�̷�����Ϣ��Ч��

## ��дprotoЭ���ļ�������
    
    1����дproto�ļ� Users.proto
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
    2����װmaven���
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
                        <!--Ĭ��ֵ-->
                        <protoSourceRoot>${project.basedir}/src/main/proto</protoSourceRoot>
                        <!--Ĭ��ֵ-->
                        <!--<outputDirectory>${project.build.directory}/generated-sources/protobuf/java</outputDirectory>-->
                        <outputDirectory>${project.basedir}/src/main/java</outputDirectory>
                        <!--�����Ƿ�������java�ļ�֮ǰ���outputDirectory���ļ���Ĭ��ֵΪtrue������ΪfalseʱҲ�Ḳ��ͬ���ļ�-->
                        <clearOutputDirectory>false</clearOutputDirectory>
                        <!--����������Ϣ���Բ鿴https://www.xolstice.org/protobuf-maven-plugin/compile-mojo.html-->
                    </configuration>
                    <executions>
                        <execution>
                            <!--��ִ��mvn compile��ʱ���ִ�����²���-->
                            <phase>compile</phase>
                            <goals>
                                <!--����OuterClass��-->
                                <goal>compile</goal>
                                <!--����Grpc��-->
                                <goal>compile-custom</goal>
                            </goals>
                        </execution>
                    </executions>
                </plugin>
            </plugins>
        </build>
    3�����compile������Ŀ���Ὣָ���ļ����µ�proto�ļ������ProtobufЭ����ļ�
        �ο� grpc-libģ��
        