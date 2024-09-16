package com.supershy.moviepedia.review.service;

import com.supershy.moviepedia.review.dto.ReviewDto;

import java.util.List;

public interface ReviewService {

    void createReview(Long memberId, Long movieId, ReviewDto reviewDto);

    ReviewDto getReview(Long reviewId, Long movieId);

    ReviewDto updateReview(Long memberId, Long movieId, Long reviewId, ReviewDto reviewDto);

    void deleteReview(Long reviewId, Long memberId, Long movieId);

    List<ReviewDto> getReviewList(Long movieId);
}
