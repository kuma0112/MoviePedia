package com.supershy.moviepedia.member.service.impl;

import com.supershy.moviepedia.auth.dto.LoginRequestDto;
import com.supershy.moviepedia.auth.utils.JwtTokenProvider;
import com.supershy.moviepedia.common.exception.InvalidCredentialsException;
import com.supershy.moviepedia.common.exception.MemberNotFoundException;
import com.supershy.moviepedia.common.exception.RegistrationException;
import com.supershy.moviepedia.like.repository.LikeRepository;
import com.supershy.moviepedia.member.dto.MemberDto;
import com.supershy.moviepedia.member.dto.MyMovieDto;
import com.supershy.moviepedia.member.dto.MyPageDto;
import com.supershy.moviepedia.member.dto.MyPageReviewDto;
import com.supershy.moviepedia.member.entity.Member;
import com.supershy.moviepedia.member.repository.MemberRepository;
import com.supershy.moviepedia.member.service.MemberService;
import com.supershy.moviepedia.movie.entity.Movie;
import com.supershy.moviepedia.movie.repository.MovieRepository;
import com.supershy.moviepedia.review.entity.Review;
import com.supershy.moviepedia.review.repository.ReviewRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;
    private final LikeRepository likeRepository;

    public MemberServiceImpl(MemberRepository memberRepository, PasswordEncoder passwordEncoder,
                             JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager, ReviewRepository reviewRepository, MovieRepository movieRepository, LikeRepository likeRepository) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.reviewRepository = reviewRepository;
        this.movieRepository = movieRepository;
        this.likeRepository = likeRepository;
    }


    @Override
    public void registerMember(MemberDto memberDto) {

        // 이메일 중복 체크
        if (memberRepository.existsByEmail(memberDto.getEmail())) {
            throw new RegistrationException("이미 사용 중인 이메일입니다.");
        }

        // 닉네임 중복 체크
        if (memberRepository.existsByNickname(memberDto.getNickname())) {
            throw new RegistrationException("이미 사용 중인 닉네임입니다.");
        }

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

    @Override
    public MyPageDto getMyPage(Member member) {
        List<Review> reviews = reviewRepository.findAllByMember_MemberId(member.getMemberId());
        List<MyPageReviewDto> reviewDtoList = reviews.stream()
                .map(MyPageReviewDto::fromEntity)
                .collect(Collectors.toList());
        return new MyPageDto(member.getNickname(), reviewDtoList);
    }

    @Override
    public List<MyMovieDto> getMyMovie(Member member) {
        List<Movie> movieList = likeRepository.findAllByMemberId(member.getMemberId());
        return movieList.stream()
                .map(MyMovieDto::fromEntity)
                .toList();
    }

}
