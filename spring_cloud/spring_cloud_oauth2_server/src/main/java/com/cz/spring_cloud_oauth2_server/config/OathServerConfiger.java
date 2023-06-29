package com.cz.spring_cloud_oauth2_server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;

/**
 * 当前类为Oauth2 server的配置类，需要继承特定的父类
 */
@Configuration
@EnableAuthorizationServer // 开启认证服务器功能
public class OathServerConfiger extends AuthorizationServerConfigurerAdapter {

    @Autowired
    public AuthenticationManager authenticationManager;

    /**
     * 客户端详情配置,
     * 比如client_id, secret
     * 当前这个服务就如同QQ、微信这类授权平台
     * 我们的其他需要授权的系统，就相当于平常使用的第三方软件，需要来此服务注册，获取到client_id, secret等必要参数
     * 表明客户端是谁
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        super.configure(clients);
        clients.inMemory() // 客户端信息存储媒介, 内存
                .withClient("client_jzm") // 添加一个client配置
                .secret("jzm")                     // 客户端安全码
                .resourceIds("user", "consumer")   // 指定客户端能访问的组员, 此处的资源id需要在具体的资源服务器上也配置一样的
                // 令牌颁发模式, 可以配置多个，具体使用哪种方式，需要客户端传递参数
                .authorizedGrantTypes("password", "refresh_token")
                // 客户端的权限范围
                .scopes("all");

        // 改造支持数据库存储客户端数据
        // 当配置了从数据库加载之后，内存中的客户端配置信息将失效
        clients.withClientDetails(this.jdbcClientDetailsService());

    }

    @Autowired
    private DataSource dataSource;

    @Bean
    public JdbcClientDetailsService jdbcClientDetailsService(){
        JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(dataSource);
        return jdbcClientDetailsService;
    }

    /**
     * 配置token令牌相关
     * token是一个字符串，需要对token进行存储, 在这里配置存储方式
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        super.configure(endpoints);
        endpoints
                .tokenStore(this.tokenStore()) // 指定token的存储方法
                // token服务的一个描述，可以认为是token生成细节的描述， 比如有效时间多少等
                .tokenServices(this.authorizationServerTokenServices())
                .authenticationManager(authenticationManager) // 指定认证管理器
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);


    }

    /**
     * 令牌存储器
     *  token以什么形式存储
     * @return
     */
    public TokenStore tokenStore(){
//        return new InMemoryTokenStore();

        return new JwtTokenStore(this.jwtAccessTokenConverter());
    }

    private String key = "jzm123";

    @Autowired
    private AccessTokenConverter tokenConverter;
    /**
     * jwt秘钥转换器
     * @return
     */
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(key); // 签名秘钥
        converter.setVerifier(new MacSigner(key)); // 验证使用的秘钥，和签名时的秘钥保持一致

        // 设置自定义的token转换器，实现在jwt中存放额外的信息
        converter.setAccessTokenConverter(tokenConverter);
        return converter;
    }

    /**
     * 获取token服务对象，描述了token有效期对象
     * @return
     */
    public AuthorizationServerTokenServices authorizationServerTokenServices(){
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setTokenStore(this.tokenStore());
        // 设置令牌有效期
        defaultTokenServices.setAccessTokenValiditySeconds(100);
        // 设置刷新令牌有效时间
        defaultTokenServices.setRefreshTokenValiditySeconds(7200);

        // 支持JWT
        defaultTokenServices.setTokenEnhancer(this.jwtAccessTokenConverter());
        return defaultTokenServices;
    }

    /**
     * 认证服务器，最终是以api接口方式对外提供服务，校验合法性并生成令牌，校验令牌等
     * 那么，以api接口方式对外的话，就涉及到接口的访问权限，就需要在这里进行必要的配置
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        super.configure(security);
        // 相当于打开endpoint访问接口的开关，后期才能访问该接口
        security
                // 允许客户端表单认证
                .allowFormAuthenticationForClients()
                // 开启端口/oauth/token_key的访问权限
                .tokenKeyAccess("permitAll()")
                // 开启端口/oauth/check_token的访问权限
                .checkTokenAccess("permitAll()");

    }

}
