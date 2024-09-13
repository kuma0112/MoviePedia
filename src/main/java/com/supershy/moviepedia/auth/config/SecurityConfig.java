package com.supershy.moviepedia.auth.config;

import com.supershy.moviepedia.auth.filter.JwtAuthenticationFileter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
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
                        .requestMatchers("/api/members/" ,"/api/members/login").permitAll()
                        .anyRequest().authenticated()
                )

                .formLogin(formLogin -> formLogin.disable())
                .logout(logout -> logout.disable());

        return  http.build();
    }

}
