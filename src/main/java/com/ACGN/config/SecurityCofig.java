package com.ACGN.config;


import com.ACGN.Service.AuthorizeService;
import com.ACGN.Service.UserService;
import com.ACGN.util.R;
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
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;


@Configuration
@EnableWebSecurity
public class SecurityCofig {
    @Autowired
    AuthorizeService userService;
    @Resource
    DataSource dataSource;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    return http.authorizeHttpRequests()
            .antMatchers("/regist/**","/classified","/index","/classification","/images/**")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            .loginProcessingUrl("/login")
            .successHandler(this::onAuthenticationSuccess)
            .failureHandler(this::onAuthenticationFailure)
            .and()
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessHandler(this::onAuthenticationSuccess)
//            .and()
//            .rememberMe()
//            .rememberMeParameter("remember")
//            .tokenRepository(this.tokenRepository())
//            .tokenValiditySeconds(3600*24*7)
            .and()
            .csrf()
            .disable()
            .cors()
            .configurationSource(this.configurationSource())
            .and()
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
        if(request.getRequestURI().endsWith("/login")){
            response.getWriter().write(JSONObject.toJSONString(RUtils.success(authentication.getName())));}

        else if(request.getRequestURI().endsWith("/logout")){
            R r=new R();
            r.setCode(200);
            r.setMsg("退出成功");
            response.getWriter().write(JSONObject.toJSONString(r));}

    }


    private void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,  AuthenticationException e) throws IOException{
        System.out.println(e.getMessage());
        response.getWriter().write(JSONObject.toJSONString(RUtils.Err(Renum.PASSWORD_ERROR.getCode(),Renum.PASSWORD_ERROR.getMsg())));
    }



    private void onAuthenticationAuthorization(HttpServletRequest request, HttpServletResponse response,  AuthenticationException e) throws IOException{
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(JSONObject.toJSONString(RUtils.Err(401,e.getMessage())));

    }


    private CorsConfigurationSource configurationSource(){
        CorsConfiguration cors=new CorsConfiguration();
        cors.addAllowedOriginPattern("*");
        cors.setAllowCredentials(true);
        cors.addAllowedHeader("*");
        cors.addAllowedMethod("*");
        cors.addExposedHeader("*");
        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",cors);
        return source;
    }

    private PersistentTokenRepository tokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository=new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        System.out.println("-------------------");
        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }



}
