package com.tangwh.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * Token
 */
@Configuration
public class AcessTokenConfig {



//  不用redis存  使用jwt
//    @Autowired
//    RedisConnectionFactory redisConnectionFactory;
    /**
     * Token(令牌)保存在内存中  return new InMemoryTokenStore();
     * @return
     *  存在redis中 RedisTokenStore
     */
    @Bean
    TokenStore tokenStore(){
 //保存在内存中
//        return new InMemoryTokenStore();
        // 存在redis中
//        return new RedisTokenStore(redisConnectionFactory);

        // token   变成 jwt格式
        return new JwtTokenStore(jwtAccessTokenConverter());

    }
    private String SIGNING_KEY = "javaboy";


    /**
     * Jwt 帮忙生成的UUID 转换成 jwt的格式
     * @return
     */
    @Bean
    JwtAccessTokenConverter jwtAccessTokenConverter(){

        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        //存令牌的 key 不能串改 相当于密钥
        converter.setSigningKey(SIGNING_KEY);
        return converter;

    }
}
