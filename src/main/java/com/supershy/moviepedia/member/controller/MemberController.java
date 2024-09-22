package com.supershy.moviepedia.member.controller;

import com.supershy.moviepedia.auth.dto.LoginRequestDto;
import com.supershy.moviepedia.member.dto.MemberDto;
import com.supershy.moviepedia.member.dto.MessageResponse;
import com.supershy.moviepedia.member.dto.MyMovieDto;
import com.supershy.moviepedia.member.dto.MyPageDto;
import com.supershy.moviepedia.member.entity.Member;
import com.supershy.moviepedia.member.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import java.util.Map;

@RestController
@RequestMapping("/api/members") // 기본 URL 설정
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 회원가입 요청 처리
    @PostMapping
    public ResponseEntity<?> registerMember(@Valid @RequestBody MemberDto memberDto) {
        try {
            memberService.registerMember(memberDto);
            return ResponseEntity.ok(new MessageResponse("회원가입이 성공적으로 이루어졌습니다."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    // 로그인 요청 처리
    @PostMapping("/login")
    public ResponseEntity<?> loginMember(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        try {
            Map<String, String> loginResponse = memberService.loginMember(loginRequestDto);
            String token = loginResponse.get("token");
            String nickname = loginResponse.get("nickname");

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .body(Map.of("nickname", nickname));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/mypage")
    public ResponseEntity<?> getMyPage(@AuthenticationPrincipal(expression = "member") Member member) {
        MyPageDto myPageDto = memberService.getMyPage(member);
        return ResponseEntity.status(HttpStatus.OK).body(myPageDto);
    }

    @GetMapping("/mymovie")
    public ResponseEntity<?> getMyMovie(@AuthenticationPrincipal(expression = "member") Member member) {
        List<MyMovieDto> myMovieDto = memberService.getMyMovie(member);
        return ResponseEntity.status(HttpStatus.OK).body(myMovieDto);
    }
}
