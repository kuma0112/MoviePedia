package com.supershy.moviepedia.member.service;

import com.supershy.moviepedia.auth.dto.LoginRequestDto;
import com.supershy.moviepedia.member.dto.MemberDto;
import com.supershy.moviepedia.member.dto.MyPageDto;
import com.supershy.moviepedia.member.entity.Member;

public interface MemberService {
    void registerMember(MemberDto memberDto);
    String loginMember(LoginRequestDto loginRequestDto);
    MyPageDto getMyPage(Member member);
}
