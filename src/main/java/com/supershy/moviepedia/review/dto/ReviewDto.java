package com.supershy.moviepedia.review.dto;

import com.supershy.moviepedia.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private String content;
    private String nickname;

    public static ReviewDto fromEntity(Review review) {
        return new ReviewDto(review.getContent(), review.getMember().getNickname());
    }
}
