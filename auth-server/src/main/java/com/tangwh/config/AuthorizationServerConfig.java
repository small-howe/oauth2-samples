package com.tangwh.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.sql.DataSource;

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
     * (授权码模式)授权码 存在内存中  通过授权码拿到令牌
     * @return
     */
    AuthorizationCodeServices authorizationCodeServices(){

        return new InMemoryAuthorizationCodeServices();
    }
    /**
     * 客户端的信息 详情信息 存在数据库中 所以不需要了
     */
//    @Autowired
//    ClientDetailsService clientDetailsService;
    @Autowired
    DataSource dataSource;
    // 为了将客户端信息从数据库中读取

    @Bean
    ClientDetailsService clientDetailsService(){

        return new JdbcClientDetailsService(dataSource);
    }
    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * 配置token 服务
     * 比如 令牌有效期限  存在哪里  刷新令牌有效期
     */
    @Bean
    AuthorizationServerTokenServices tokenServices(){
        DefaultTokenServices services = new DefaultTokenServices();
        // 之前是写死 现在是存在数据库中
//        services.setClientDetailsService(clientDetailsService);
        services.setClientDetailsService(clientDetailsService());
        // 是否支持刷新
        services.setSupportRefreshToken(true);
        // 生成的Token保存在哪里
        services.setTokenStore(tokenStore);
        // 关于客户端信息 存在数据库中 所以自己就不用设置
//        // 有效期 2个小时
//        services.setAccessTokenValiditySeconds(60 * 60 * 2);
//        // 刷新Toeken 时间 7天
//        services.setRefreshTokenValiditySeconds(60 *60 *21 *7);
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
      security.checkTokenAccess("permitAll()")   // oauth/check_token接口名 检查token效性 等
       .allowFormAuthenticationForClients(); // 允许表单登录
    }


    /**
     * 配置客户端信息 可以配置在内存中 也可以配置在数据库中 本次操作 配置在内存中
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {

        // 写死的
//       clients.inMemory()
//               // 客户端的 ID
//               .withClient("javaboy")
//               // 客户端的密码
//               .secret(passwordEncoder.encode("123"))
//               // 资源id
//               .resourceIds("res1")
//               // 四种授权的模式 --  authorization_code 授权码模式  refresh_token 刷新token 会用到
//               //"implicit" 简化模式的配置
//               //"password" 密码模式
//               //"client_credentials" 客户端模式
//               .authorizedGrantTypes("authorization_code", "refresh_token", "implicit", "password","client_credentials")
//               // 客户端 授权范围
//               .scopes("all")
//               // 授权自动批准 不用询问是否授权
//               .autoApprove(true)
//               // 授权完成之后 的一个地址 重定向(授权码模式的地址)
////               .redirectUris("http://localhost:8082/index.html");
//               // 授权完成之后 的一个地址 重定向 (简化模式的地址)
////               .redirectUris("http://localhost:8082/index.html");
////               http://localhost:8082/01.html静态页面 http://localhost:8082/02.html动态页面(密码配置模式)
//               .redirectUris("http://localhost:8082/01.html","http://localhost:8082/02.html");


        // 从数据库中读取的
        clients.withClientDetails(clientDetailsService());
    }

    /**
     * 密码模式必须用到 配置  前提 需要在Security提供一下
     */
    @Autowired
    AuthenticationManager authenticationManager;
    /**
     * 端点的信息
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {


        endpoints.
                // 配置授权码模式
                authorizationCodeServices(authorizationCodeServices())
                /// 令牌
                .tokenStore(tokenStore)
                // 密码模式配置
               .authenticationManager(authenticationManager);


    }
}
