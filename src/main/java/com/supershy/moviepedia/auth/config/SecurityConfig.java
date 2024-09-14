package com.supershy.moviepedia.auth.config;

import com.supershy.moviepedia.auth.filter.JwtAuthenticationFileter;
import com.supershy.moviepedia.auth.utils.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
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
                        .requestMatchers("/api/members").permitAll() // 회원가입 엔드포인트
                        .requestMatchers("/api/members/login").permitAll() // 로그인 엔드포인트
                        .anyRequest().authenticated()) // 그 외 모든 요청은 인증 필요
                .addFilterBefore(new JwtAuthenticationFileter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class)
                .formLogin(formLogin -> formLogin.disable())
                .logout(logout -> logout.disable());

        return  http.build();
    }

}
