package com.supershy.moviepedia.member.service.impl;

import com.supershy.moviepedia.member.dto.MemberDto;
import com.supershy.moviepedia.member.entity.Member;
import com.supershy.moviepedia.member.repository.MemberRepository;
import com.supershy.moviepedia.member.service.MemberService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberServiceImpl(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
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
}
