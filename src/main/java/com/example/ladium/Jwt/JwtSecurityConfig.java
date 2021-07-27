package com.example.ladium.Jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    //extends SecurityConfigurerAdpater하여 configure 메소드를 오버라이드하여 JwtFilter를 Security 로직에 적용하는 역할
    private final TokenProvider tokenProvider;

    public JwtSecurityConfig(TokenProvider tokenProvider){
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void configure(HttpSecurity http){
        JwtAuthenticationFilter customFilter = new JwtAuthenticationFilter(tokenProvider);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }

//    public void addFilterBefore(JwtAuthenticationFilter jwtAuthenticationFilter, Class<UsernamePasswordAuthenticationFilter> usernamePasswordAuthenticationFilterClass) {
//    }
}
