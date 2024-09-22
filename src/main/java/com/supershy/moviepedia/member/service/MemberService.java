package com.supershy.moviepedia.member.service;

import com.supershy.moviepedia.auth.dto.LoginRequestDto;
import com.supershy.moviepedia.member.dto.MemberDto;
import com.supershy.moviepedia.member.dto.MyMovieDto;
import com.supershy.moviepedia.member.dto.MyPageDto;
import com.supershy.moviepedia.member.entity.Member;

import java.util.List;
import java.util.Map;

public interface MemberService {
    void registerMember(MemberDto memberDto);
    Map<String, String> loginMember(LoginRequestDto loginRequestDto);
    MyPageDto getMyPage(Member member);
    List<MyMovieDto> getMyMovie(Member member);
}
