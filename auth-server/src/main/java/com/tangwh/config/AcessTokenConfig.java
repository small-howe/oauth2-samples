package com.tangwh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * Token
 */
@Configuration
public class AcessTokenConfig {

    /**
     * Token保存在内存中
     * @return
     */
    @Bean
    TokenStore tokenStore(){

        return new InMemoryTokenStore();

    }
}
