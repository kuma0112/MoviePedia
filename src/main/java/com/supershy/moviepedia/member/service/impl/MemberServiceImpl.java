package com.supershy.moviepedia.member.service.impl;

import com.supershy.moviepedia.auth.dto.LoginRequestDto;
import com.supershy.moviepedia.auth.utils.JwtTokenProvider;
import com.supershy.moviepedia.member.dto.MemberDto;
import com.supershy.moviepedia.member.dto.MyPageDto;
import com.supershy.moviepedia.member.dto.MyPageReviewDto;
import com.supershy.moviepedia.member.entity.Member;
import com.supershy.moviepedia.member.repository.MemberRepository;
import com.supershy.moviepedia.member.service.MemberService;
import com.supershy.moviepedia.review.entity.Review;
import com.supershy.moviepedia.review.repository.ReviewRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final ReviewRepository reviewRepository;

    public MemberServiceImpl(MemberRepository memberRepository, PasswordEncoder passwordEncoder,
                             JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager, ReviewRepository reviewRepository) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.reviewRepository = reviewRepository;
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
    public String loginMember(LoginRequestDto loginRequestDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
            );
            return jwtTokenProvider.createToken(authentication.getName());
        } catch (AuthenticationException e) {
            throw new RuntimeException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }
    }

    @Override
    public MyPageDto getMyPage(Member member) {
        List<Review> reviews = reviewRepository.findAllByMember_MemberId(member.getMemberId());
        List<MyPageReviewDto> reviewDtoList = reviews.stream()
                .map(MyPageReviewDto::fromEntity)
                .collect(Collectors.toList());
        return new MyPageDto(member.getNickname(), reviewDtoList);
    }

}
