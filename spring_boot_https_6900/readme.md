##项目中配置HTTPS

### 证书
        1、申请整数, 免费 or 收费
        2、使用Java工具生成
            keytool -genkey -alias tomcathttps -keyalg RSA -keysize 2048  -keystore D:\javaboy.p12 -validity 365
            命令含义如下:
                genkey 表示要创建一个新的密钥。
                alias 表示 keystore 的别名。
                keyalg 表示使用的加密算法是 RSA ，一种非对称加密算法。
                keysize 表示密钥的长度。
                keystore 表示生成的密钥存放位置。
                validity 表示密钥的有效时间，单位为天
### 项目配置
        1、将生成的证书拷贝到项目目录下
        2、yml中进行配置
            server:
                ssl:
                key-store: classpath:jzm.p12
                key-alias: jzm
                key-store-password: 123456
        3、重启项目，使用https协议进行接口请求

### 请求转发
        如果项目是中途升级成HTTPS协议,可能会有客户端例如APP等未更新依然使用HTTP协议进行请求
        故需要在项目中配置请求转发,将HTTP协议的请求转发到HTTPS请求

        配置类：
            @Configuration
            public class TomcatConfig {
            
                @Value("${server.port}")
                private Integer port;
            
                @Bean
                TomcatServletWebServerFactory tomcatServletWebServerFactory() {
                    TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory(){
                        @Override
                        protected void postProcessContext(Context context) {
                            SecurityConstraint constraint = new SecurityConstraint();
                            constraint.setUserConstraint("CONFIDENTIAL");
                            SecurityCollection collection = new SecurityCollection();
                            collection.addPattern("/*");
                            constraint.addCollection(collection);
                            context.addConstraint(constraint);
                        }
                    };
                    factory.addAdditionalTomcatConnectors(createTomcatConnector());
                    return factory;
                }
                private Connector createTomcatConnector() {
                    Connector connector = new
                            Connector("org.apache.coyote.http11.Http11NioProtocol");
                    connector.setScheme("http");
                    connector.setPort(6901);
                    connector.setSecure(false);
                    connector.setRedirectPort(port);
                    return connector;
                }
            }
            
        加入原项目使用6901端口,旧客户端使用HTTP对6901端口发起请求，会被重新转发到HTTPS协议的6900端口