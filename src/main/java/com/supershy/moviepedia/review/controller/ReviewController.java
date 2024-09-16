package com.supershy.moviepedia.review.controller;

import com.supershy.moviepedia.review.dto.ReviewDto;
import com.supershy.moviepedia.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies/{movieId}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<String> createReview(@PathVariable Long movieId, @RequestBody ReviewDto reviewDto) {
        Long memberId = 1L;
        reviewService.createReview(memberId, movieId, reviewDto);
        return ResponseEntity.ok("리뷰가 성공적으로 생성되었습니다.");
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDto> getReview(@PathVariable Long movieId, @PathVariable Long reviewId) {
        return ResponseEntity.ok(reviewService.getReview(reviewId, movieId));
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable Long movieId, @PathVariable Long reviewId, @RequestBody ReviewDto reviewDto) {
        Long memberId = 1L;
        return ResponseEntity.ok(reviewService.updateReview(memberId, movieId, reviewId, reviewDto));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long movieId, @PathVariable Long reviewId) {
        Long memberId = 1L;
        reviewService.deleteReview(reviewId, memberId, movieId);
        return ResponseEntity.ok("리뷰가 성공적으로 삭제되었습니다.");
    }

    @GetMapping
    public ResponseEntity<List<ReviewDto>> getReviewList(@PathVariable Long movieId) {
        List<ReviewDto> reviewDtos = reviewService.getReviewList(movieId);
        return ResponseEntity.ok(reviewDtos);
    }
}
