package com.tangwh.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 资源服务器
 */
@Configuration
// 开启资源服务器
@EnableResourceServer
public class ResoureServerConfig extends ResourceServerConfigurerAdapter {


// 资源服务器 拿到jwt解析获取到用户的信息
    @Autowired
    TokenStore tokenStore;
    // 远程 TokenServer 校验 如果用jwt 就不需要远程
//    @Bean
//    RemoteTokenServices tokenServices() {
//        RemoteTokenServices services = new RemoteTokenServices();
//
//        //用户从资源服务器拿数据 传递token  去授权服务器检测token 对不对
//        services.setCheckTokenEndpointUrl("http://localhost:8080/oauth/check_token");
//
//        // 连接 需要 客户端的信息 id 和密码
//        services.setClientId("javaboy");
//        services.setClientSecret("123");
//
//        return services;
//    }


    /**
     * 资源配置
     * @param resources
     * @throws Exception
     */
    @Override
    public void configure(final ResourceServerSecurityConfigurer resources) throws Exception {
        // 资源id 在 授权服务器上 配置多少 这个就是多少
//      resources.resourceId("res1").tokenServices(tokenServices());
        //使用jwt后的配置
        resources.resourceId("res1").tokenStore(tokenStore);
    }

    /**
     * 拦截的一些规则
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("admin")
                // 其他的 都是 登录成功后就能访问的
                .anyRequest().authenticated()
                //支持跨域
                .and().cors();

    }
}
