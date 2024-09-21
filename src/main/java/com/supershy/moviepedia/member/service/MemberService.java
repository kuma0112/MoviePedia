package com.supershy.moviepedia.member.service;

import com.supershy.moviepedia.auth.dto.LoginRequestDto;
import com.supershy.moviepedia.member.dto.MemberDto;

import java.util.Map;

public interface MemberService {
    void registerMember(MemberDto memberDto);
    Map<String, String> loginMember(LoginRequestDto loginRequestDto);
}
