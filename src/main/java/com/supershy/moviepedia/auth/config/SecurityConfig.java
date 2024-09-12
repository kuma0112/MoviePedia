package com.supershy.moviepedia.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception{
        http
                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화
                .httpBasic(httpBasic -> httpBasic.disable()) // HTTP Basic 인증 비활성화
                .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS // JWT TOken 인증방식으로 세션을 필요 없으므로 비활성화
                ))
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/members").access()
//                        .requestMatchers("/api/members/login").access()
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin.disable())
                .logout(logout -> logout.disable());

        return  http.build();
    }

}
