package com.supershy.moviepedia.member.dto;

import com.supershy.moviepedia.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MyPageReviewDto {
    private Long reviewId;
    private String movieTitle;
    private Long movieId;
    private String content;
    private String nickname;

    public static MyPageReviewDto fromEntity(Review review) {
        return new MyPageReviewDto(
                review.getReviewId(),
                review.getMovie().getTitle(),
                review.getMovie().getMovieId(),
                review.getContent(),
                review.getMember().getNickname());
    }
}
