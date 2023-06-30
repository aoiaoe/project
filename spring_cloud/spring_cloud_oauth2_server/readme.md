# 基于spring cloud oauth2实现的认证服务器

    架构图，参考: oauth2用户密码方式的认证服务器.png

## 公约
    因为认证服务器和资源服务器 需要引入的Oauth2的依赖包相同
    所以提取了一个公共module spring_cloud_oauth2_dependency， 认证服务器和资源服务器引用此module即可
## 认证服务器实现 步骤
    1、引入oauth2依赖，见上方公约
    2、实现Oauth2 server的配置类, 需要继承特定的父类
        参考: com.cz.spring_cloud_oauth2_server.config.OathServerConfiger.java
    3、继承WebSecurityConfigurerAdapter.java类, 实现对Security的配置
        参考: com.cz.spring_cloud_oauth2_server.config.SecurityConfigurer.java
## 资源服务器实现 步骤
    使用 module spring_cloud_provider_user_9105 实现资源服务器
    1、引入oauth2依赖，见上方公约
    2、实现oauth2 resource server配置类m 需继承特定父类
        参考： com.cz.springcloudprovideruser.config.ResourceServerConfigurer.java


# 改造Oauth2支持JWT

## 改造认证服务器
    1、认证服务器配置类 com.cz.spring_cloud_oauth2_server.config.OathServerConfiger.java
        修改TokenStore实现对象为 JwtTokenStore
        修改TokenServices支持JWT

## 改造资源服务器
    1、资源服务器配置类 com.cz.springcloudprovideruser.config.ResourceServerConfigurer
        修改认证方式为TokenStore， 而非TokenServices
        注意： TokenStore需要和服务端的实现相同，即加解密秘钥对应，否则无法校验通过


# 改造认证服务器从数据库加载数据信息，而非内存
    修改认证服务器配置类 com.cz.spring_cloud_oauth2_server.config.OathServerConfiger.java
    1、客户端配置信息
        修改 configure(ClientDetailsServiceConfigurer clients)方法
        使服务器从数据库中加载客户端配置信息
    2、用户信息
        a、引入持久层框架依赖
        b、建立users表，存储用户数据、定义Users.java实体类，
            定义 com.cz.spring_cloud_oauth2_server.dao.UserRepository.java dao层接口
        c、实现 UserDetailsService接口，用于给框架获取UserDetails对象
            参考: com.cz.spring_cloud_oauth2_server.service.JdbcUserDetailsService.java
        d、因为用户是Security框架层使用的东西, 故需要修改Security配置类
            修改 protected void configure(AuthenticationManagerBuilder auth) 方法
            将用户认证改为从数据中获取数据，而非内存数据

# 调用链路token透传
    对于一个庞杂的系统，肯定会存在A服务调用B服务，
    如果B服务里面的接口也被配置为需要认证，则A服务需要将认证信息传递到B服务区，
    如果对于客户端，是使用OpenFeign的方式，则可以参考模块spring_cloud_consumer_feign_9202 中的
        com.cz.springcloudconsumerfeign.config.FeignInterceptor.java配置，进行服务间的header传递

# OAUTH2 + JWT 实现自定义扩展jwt中载荷的信息
    在[认证服务器]中,
    1、实现自定义转换器, 继承自 DefaultAccessTokenConverter.java
        重写public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication)方法
        参考: com.cz.spring_cloud_oauth2_server.config.ExtraInfoAccessTokenConverter.java
    2、修改JwtAccessTokenConverter的converter
        方法： public JwtAccessTokenConverter jwtAccessTokenConverter()
        参考: com.cz.spring_cloud_oauth2_server.config.OathServerConfiger.java

# 使用JWT中载荷的信息
    在[资源服务器]中
    1、实现自定义转换器, 继承自 DefaultAccessTokenConverter.java
        重写： public OAuth2Authentication extractAuthentication(Map<String, ?> map) 方法
        参考: com.cz.springcloudprovideruser.config.ExtraInfoAccessTokenConverter.java
    2、修改JwtAccessTokenConverter的converter，实现将载荷信息放入认证信息中，后续可以使用
        方法： public JwtAccessTokenConverter jwtAccessTokenConverter()
        参考: com.cz.springcloudprovideruser.config.ResourceServerConfigurer.java
    3、后续使用
        使用一下代码，可以在业务代码中获取到载荷信息
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        Map decodedDetails = (Map) details.getDecodedDetails();

# OAUTH2 接口
    1、获取/刷新token接口: GET /oauth/token
            所在类：org.springframework.security.oauth2.provider.endpoint.TokenEndpoint.java
            获取token示例: localhost:12999/auth/oauth/token?client_secret=jzm12345&grant_type=password&username=jzm&password=jjjzzzmmm&client_id=jzm123
            刷新token示例: localhost:11999/oauth/token?grant_type=refresh_token&client_id=client_jzm&client_secret=jzm&refresh_token=9e7babb7-2005-48b2-b1e9-6b1ec6a87531
    2、检查token接口： /oauth/check_token
            所在类: org.springframework.security.oauth2.provider.endpoint.CheckTokenEndpoint.java
            示例: localhost:11999/oauth/check_token?token=xxxx
    