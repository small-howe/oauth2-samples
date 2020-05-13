package com.tangwh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig  extends WebSecurityConfigurerAdapter {




    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // 用户信息存在内存里面
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("sang")
                .password(passwordEncoder().encode("123"))
                .roles("admin")
                .and()
                .withUser("javaboy")
                .password(passwordEncoder().encode("123"))
                .roles("user");
    }


    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        // 关闭 csrf  开启表单登录
        http.csrf().disable().formLogin();
    }
}
