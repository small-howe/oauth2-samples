package com.tangwh.config;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * 自定义返回jwt信息
 */
@Component
public class MyAdditionalInformation implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(final OAuth2AccessToken accessToken, final OAuth2Authentication authentication) {

        Map<String, Object> map = accessToken.getAdditionalInformation();
        map.put("site", "www.baidu.com");
        map.put("author", "唐同学");
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(map);

        return accessToken;


    }
}
