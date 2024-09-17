package com.supershy.moviepedia.review.controller;

import com.supershy.moviepedia.member.entity.Member;
import com.supershy.moviepedia.review.dto.ReviewDto;
import com.supershy.moviepedia.review.dto.ReviewListDto;
import com.supershy.moviepedia.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies/{movieId}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<String> createReview(@PathVariable Long movieId, @RequestBody ReviewDto reviewDto, @AuthenticationPrincipal Member member) {
        reviewService.createReview(member, movieId, reviewDto);
        return ResponseEntity.ok("리뷰가 성공적으로 생성되었습니다.");
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDto> getReview(@PathVariable Long movieId, @PathVariable Long reviewId) {
        return ResponseEntity.ok(reviewService.getReview(reviewId, movieId));
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable Long movieId, @PathVariable Long reviewId,
                                                  @RequestBody ReviewDto reviewDto, @AuthenticationPrincipal Member member) {
        return ResponseEntity.ok(reviewService.updateReview(member, movieId, reviewId, reviewDto));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long movieId, @PathVariable Long reviewId
                                                , @AuthenticationPrincipal Member member) {
        reviewService.deleteReview(reviewId, member, movieId);
        return ResponseEntity.ok("리뷰가 성공적으로 삭제되었습니다.");
    }

    @GetMapping
    public ResponseEntity<ReviewListDto> getReviewList(@PathVariable Long movieId,
                                                       @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        ReviewListDto reviewListDto = reviewService.getReviewList(movieId, page, size);
        return ResponseEntity.ok(reviewListDto);
    }
}
