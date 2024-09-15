package com.supershy.moviepedia.member.service;

import com.supershy.moviepedia.auth.dto.LoginRequestDto;
import com.supershy.moviepedia.member.dto.MemberDto;

public interface MemberService {
    void registerMember(MemberDto memberDto);
    String loginMember(LoginRequestDto loginRequestDto);
}
