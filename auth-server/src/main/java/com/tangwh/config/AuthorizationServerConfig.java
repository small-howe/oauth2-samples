package com.tangwh.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 授权服务器
 *   校验 ：1 用户信息  2 客户端的信息 比如网站是否合法之类的
 */
@Configuration
// 开启授权服务
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * 令牌 是需要通过授权码 获取到的
     */
    @Autowired
    TokenStore tokenStore;


    /**
     * 授权码 存在内存中
     * @return
     */
    AuthorizationCodeServices authorizationCodeServices(){

        return new InMemoryAuthorizationCodeServices();
    }
    /**
     * 客户端的信息 详情信息
     */
    @Autowired
    ClientDetailsService clientDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * 配置token 服务
     * 比如 令牌有效期限  存在哪里  刷新令牌有效期
     */
    @Bean
    AuthorizationServerTokenServices tokenServices(){
        DefaultTokenServices services = new DefaultTokenServices();
        services.setClientDetailsService(clientDetailsService);
        // 是否支持刷新
        services.setSupportRefreshToken(true);
        // 生成的Token保存在哪里
        services.setTokenStore(tokenStore);
        // 有效期 2个小时
        services.setAccessTokenValiditySeconds(60 *60 *2);
        // 刷新Toeken 时间 7天
        services.setRefreshTokenValiditySeconds(60 *60 *21 *7);
        return services;

    }

    /**
     * 配置安全规则
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(final AuthorizationServerSecurityConfigurer security) throws Exception {
        // 这个地址 只要传递参数进来 就可以访问
    security.checkTokenAccess("permitAll()")// /oauth/check_token接口名 检查token效性 等
    .allowFormAuthenticationForClients(); // 允许表单登录
    }

    /**
     * 配置客户端信息 可以配置在内存中 也可以配置在数据库中 本次操作 配置在内存中
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
       clients.inMemory()
               // 客户端的 ID
               .withClient("javaboy")
               // 客户端的密码
               .secret(passwordEncoder.encode("123"))
               // 资源id
               .resourceIds("res1")
               // 四种授权的模式 --  authorization_code 授权码模式  refresh_token 刷新token 会用到
               .authorizedGrantTypes("authorization_code","refresh_token")
               // 客户端 授权范围
               .scopes("all")
               // 授权自动批准 不用询问是否授权
               .autoApprove(true)
               // 授权完成之后 的一个地址 重定向
               .redirectUris("http://localhost:8082/index.html");
    }


    /**
     * 端点的信息
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        // 配置授权码
        endpoints.authorizationCodeServices(authorizationCodeServices())
                /// 令牌
                .tokenStore(tokenStore);


    }
}
