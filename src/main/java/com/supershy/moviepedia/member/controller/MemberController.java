package com.supershy.moviepedia.member.controller;

import com.supershy.moviepedia.member.dto.MemberDto;
import com.supershy.moviepedia.member.dto.MessageResponse;
import com.supershy.moviepedia.member.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members") // 기본 URL 설정
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 회원가입 요청 처리
    @PostMapping("/")
    public ResponseEntity<?> registerMember(@Valid @RequestBody MemberDto memberDto) {
        try {
            memberService.registerMember(memberDto);
            return ResponseEntity.ok(new MessageResponse("회원가입이 성공적으로 이루어졌습니다."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}
