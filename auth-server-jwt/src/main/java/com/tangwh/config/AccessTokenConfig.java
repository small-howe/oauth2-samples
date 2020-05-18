package com.tangwh.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class AccessTokenConfig{

    private String SIGNING_KEY = "javaboy";

    @Bean
    TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    JwtAccessTokenConverter jwtAccessTokenConverter() {
        // 默认返回jwt 返回信息
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        // 返回自己定义的jwt信息
        JwtAccessTokenConverter converter = new MyJwtConvertrt();

        converter.setSigningKey(SIGNING_KEY);
        return converter;
    }
}
