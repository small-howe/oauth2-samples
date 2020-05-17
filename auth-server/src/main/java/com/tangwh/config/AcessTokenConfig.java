package com.tangwh.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * Token
 */
@Configuration
public class AcessTokenConfig {


    @Autowired
    RedisConnectionFactory redisConnectionFactory;
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
        return new RedisTokenStore(redisConnectionFactory);
    }
}
