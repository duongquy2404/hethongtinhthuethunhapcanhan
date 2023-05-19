package com.btl.sqa_n1_5.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
  @Autowired
  private UserDetailsService userDetailsService;

  @Bean
  public static PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }
  @Autowired
  private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .authorizeHttpRequests((authorize) ->
            authorize.requestMatchers("/dang-ki-tai-khoan/**","/tinh-thue").permitAll()
                .requestMatchers("/dang-nhap/**","/index/**").permitAll()
                .requestMatchers("/js/**","/css/**").permitAll()
                .requestMatchers("/error/**").permitAll()
                .requestMatchers("/home-page/**", "/khai-bao-thue/**","/","/quyet-toan-thue/**","/thanh-toan/**").hasRole("USER")
                .anyRequest().permitAll()
        ).formLogin(
            form -> form
                .loginPage("/dang-nhap")
                .failureUrl("/dang-nhap?error=true")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/home-page")
                .permitAll()
        ).logout(
            logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/index")
                .permitAll()
        )
        .exceptionHandling()
        .defaultAuthenticationEntryPointFor(new HttpStatusEntryPoint(HttpStatus.NOT_FOUND), new AntPathRequestMatcher("/public/**"))
        .authenticationEntryPoint(customAuthenticationEntryPoint)
        .and();
    return http.build();
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder());
  }
}
