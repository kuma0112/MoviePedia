package com.supershy.moviepedia.member.dto;

import com.supershy.moviepedia.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MyPageReviewDto {
    private String movieTitle;
    private String content;
    private String nickname;

    public static MyPageReviewDto fromEntity(Review review) {
        return new MyPageReviewDto(
                review.getMovie().getTitle(),
                review.getContent(),
                review.getMember().getNickname());
    }
}
