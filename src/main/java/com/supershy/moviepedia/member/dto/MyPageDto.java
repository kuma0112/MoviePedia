package com.supershy.moviepedia.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class MyPageDto {
    private String nickname;
    private List<MyPageReviewDto> reviewList;
}
