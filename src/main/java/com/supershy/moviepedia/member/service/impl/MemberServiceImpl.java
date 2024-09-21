package com.supershy.moviepedia.member.service.impl;

import com.supershy.moviepedia.auth.dto.LoginRequestDto;
import com.supershy.moviepedia.auth.utils.JwtTokenProvider;
import com.supershy.moviepedia.common.exception.InvalidCredentialsException;
import com.supershy.moviepedia.common.exception.MemberNotFoundException;
import com.supershy.moviepedia.member.dto.MemberDto;
import com.supershy.moviepedia.member.entity.Member;
import com.supershy.moviepedia.member.repository.MemberRepository;
import com.supershy.moviepedia.member.service.MemberService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public MemberServiceImpl(MemberRepository memberRepository, PasswordEncoder passwordEncoder,
                             JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }


    @Override
    public void registerMember(MemberDto memberDto) {

        // BCrypt를 사용하여 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(memberDto.getPassword());

        // Member 객체 생성
        Member member = Member.builder()
                .email(memberDto.getEmail())
                .nickname(memberDto.getNickname())
                .password(encodedPassword)
                .build();

        memberRepository.save(member);
    }

    @Override
    public Map<String, String> loginMember(LoginRequestDto loginRequestDto) {
        try {
            // 이메일과 비밀번호로 인증
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
            );

            // JWT 토큰 생성
            String token = jwtTokenProvider.createToken(authentication.getName());

            // 이메일로 DB에서 회원 조회
            Member member = memberRepository.findByEmail(loginRequestDto.getEmail())
                    .orElseThrow(() -> new MemberNotFoundException("사용자를 찾을 수 없습니다."));

            // 토큰과 닉네임 반환
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("nickname", member.getNickname());

            return response;
        } catch (AuthenticationException e) {
            throw new InvalidCredentialsException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }
    }
}
