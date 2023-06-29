package com.cz.springcloudprovideruser.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;


/**
 * 资源服务器配置
 */
@Configuration
@EnableResourceServer   // 开启资源服务器功能
@EnableWebSecurity // 开启web安全访问
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {


    /**
     * 定义资源服务器向远程认证服务器发起请求，进行token校验
     *
     * @param resources
     * @throws Exception
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
        resources
                .resourceId("user")
//                .tokenServices(this.tokenServices()) // 远程token校验器

                // jwt改造, 改造之后本地自校验，不需要请求远程认证服务器
                .tokenStore(this.tokenStore()) // jwt存储器
                .stateless(true);

    }

    /**
     * 令牌存储器
     * token以什么形式存储
     *
     * @return
     */
    public TokenStore tokenStore() {
    //        return new InMemoryTokenStore();
        return new JwtTokenStore(this.jwtAccessTokenConverter());
    }

    private String key = "jzm123";


    @Autowired
    private ExtraInfoAccessTokenConverter extraInfoAccessTokenConverter;

    /**
     * jwt秘钥转换器
     *
     * @return
     */
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(key); // 签名秘钥
        converter.setVerifier(new MacSigner(key)); // 验证使用的秘钥，和签名时的秘钥保持一致
        // 自定义token转换器
        converter.setAccessTokenConverter(extraInfoAccessTokenConverter);
        return converter;
    }

    /**
     * 远程认证服务器
     *
     * @return
     */
//    @Bean
    public RemoteTokenServices tokenServices() {
        RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
        // 校验端点
        remoteTokenServices.setCheckTokenEndpointUrl("http://localhost:11999/oauth/check_token");
        remoteTokenServices.setClientId("client_jzm");
        remoteTokenServices.setClientSecret("jzm");
        return remoteTokenServices;
    }


    /**
     * 场景： 一个服务中可能有很多资源（api接口）
     * 某一些资源需要认证， 有一些不需要认证
     * 需要对不同接口区分对待
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                // 有些有状态场景需要session， 代表需要就创建session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests()
                // 以 /user 为前缀的请求需要认证
                .antMatchers("/user/**").authenticated()
                // 其他请求不需要认证
                .anyRequest().permitAll();
    }
}
