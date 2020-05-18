package com.tangwh.config;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.LinkedHashMap;
import java.util.Map;

public class MyJwtConvertrt extends JwtAccessTokenConverter {
    @Override
    protected String encode(final OAuth2AccessToken accessToken, final OAuth2Authentication authentication) {
        Map<String ,Object> additionalInformation = new LinkedHashMap<>();
        Map<String ,Object> info = new LinkedHashMap<>();
        additionalInformation.put("author", "唐同学");
        // 添加用户信息
        additionalInformation.put("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        info.put("info", additionalInformation);

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        return super.encode(accessToken, authentication);
    }
}
