package com.cz.springcloudprovideruser.config;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ExtraInfoAccessTokenConverter extends DefaultAccessTokenConverter {

    /**
     * 提取jwt中的信息
     * @param map
     * @return
     */
    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        OAuth2Authentication oAuth2Authentication = super.extractAuthentication(map);
        oAuth2Authentication.setDetails(map); // 将map放入认证对象，可以在业务逻辑中获取到
        return oAuth2Authentication;
    }
}
