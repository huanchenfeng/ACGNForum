package com.ACGN.config;


import com.ACGN.Service.UserService;
import com.ACGN.util.RUtils;
import com.ACGN.util.Renum;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Configuration
@EnableWebSecurity
public class SecurityCofig {
    @Autowired
    UserDetailsService userService;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    return http.authorizeHttpRequests()
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            .loginProcessingUrl("/login")
            .successHandler(this::onAuthenticationSuccess)
            .failureHandler(this::onAuthenticationFailure)
            .and()
            .logout()
            .logoutUrl("/api/auth/logout")
            .and()
            .csrf()
            .disable()
            .exceptionHandling()
            //发生没授权时
            .authenticationEntryPoint(this::onAuthenticationAuthorization)
            .and()
            .build();
}
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity security) throws Exception {
        return security.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userService)
                .and()
                .build();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    private void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException{
    response.setCharacterEncoding("utf-8");
        System.out.println("进入");
        response.getWriter().write(JSONObject.toJSONString(RUtils.success(Renum.SUCCESS)));
    }
    private void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,  AuthenticationException e) throws IOException{
        System.out.println(e.getMessage());
        response.getWriter().write(JSONObject.toJSONString(RUtils.success(Renum.PASSWORD_ERROR)));
    }
    private void onAuthenticationAuthorization(HttpServletRequest request, HttpServletResponse response,  AuthenticationException e) throws IOException{
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(JSONObject.toJSONString(RUtils.Err(401,e.getMessage())));
    }
}
