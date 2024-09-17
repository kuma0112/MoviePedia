package com.supershy.moviepedia.review.service;

import com.supershy.moviepedia.member.entity.Member;
import com.supershy.moviepedia.review.dto.ReviewDto;
import com.supershy.moviepedia.review.dto.ReviewListDto;

public interface ReviewService {

    void createReview(Member member, Long movieId, ReviewDto reviewDto);

    ReviewDto getReview(Long reviewId, Long movieId);

    ReviewDto updateReview(Member member, Long movieId, Long reviewId, ReviewDto reviewDto);

    void deleteReview(Long reviewId, Member member, Long movieId);

    ReviewListDto getReviewList(Long movieId, int page, int size);
}
