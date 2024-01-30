package com.encore.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity//해당 어노테이션은 spring security설정을 custom설정하기위함
//webSecurityConfiguerAdater를 상속하는 방식은 지원종료됨
@EnableGlobalMethodSecurity(prePostEnabled = true) // 사전 사후에 인증/권한 검사 어노테이션 사용가능
public class SecurityConfig {

    @Bean
    public SecurityFilterChain myfilter(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf().disable()
//                csrf보안공격에 대한 설정을 하지 않겠다 라는 의미
                //.csrf().disable()
                //특정 url에 대해서는 보안을 인증처리 하지않고, 특정 url에 대해서는 인증처리 하겠다라는 설정
                .authorizeRequests()
                .antMatchers("/","/author/create","/author/login-page")
                .permitAll()
                .anyRequest().authenticated()
                .and()
//                만약에 세션을 사용하지 않으면 아래 내용 설정
                //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .formLogin()
                    .loginPage("/author/login-page")
//                스프링 내장메소드를 사용하기위해 /doLogin url 사용
                    .loginProcessingUrl("/doLogin")
                        .usernameParameter("email")
                        .passwordParameter("pw")
                    .successHandler(new LoginSuccessHandler())
                .and()
                .logout()
//                springSecurity의 doLogout 기능 사용
                    .logoutUrl("/doLogout")
                .and()
                .build();
    }
}