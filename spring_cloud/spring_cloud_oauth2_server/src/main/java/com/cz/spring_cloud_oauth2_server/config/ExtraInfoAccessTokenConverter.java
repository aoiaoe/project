package com.cz.spring_cloud_oauth2_server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ExtraInfoAccessTokenConverter extends DefaultAccessTokenConverter {

    @Override
    public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        Map<String, ?> stringMap = super.convertAccessToken(token, authentication);

        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("extraKey", "extraValue");
        stringObjectMap.putAll(stringMap);
        return stringObjectMap;
    }
}
