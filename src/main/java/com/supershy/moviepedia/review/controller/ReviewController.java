package com.supershy.moviepedia.review.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.supershy.moviepedia.member.entity.Member;
import com.supershy.moviepedia.review.dto.ReviewDto;
import com.supershy.moviepedia.review.dto.ReviewListDto;
import com.supershy.moviepedia.review.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/movies/{movieId}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    
    public ResponseEntity<String> createReview(@PathVariable(name = "movieId") Long movieId, @RequestBody ReviewDto reviewDto,
                                               @AuthenticationPrincipal(expression = "member") Member member) {
        if (member == null) {
            throw new IllegalArgumentException("로그인된 사용자가 없습니다.");
        }
        reviewService.createReview(member, movieId, reviewDto);
        
        return ResponseEntity.ok("리뷰가 성공적으로 생성되었습니다.");
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDto> getReview(@PathVariable(name = "movieId") Long movieId, @PathVariable(name = "reviewId") Long reviewId) {
        return ResponseEntity.ok(reviewService.getReview(reviewId, movieId));
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable(name = "movieId") Long movieId, @PathVariable(name = "reviewId") Long reviewId,
                                                  @RequestBody ReviewDto reviewDto,
                                                  @AuthenticationPrincipal(expression = "member") Member member) {
        return ResponseEntity.ok(reviewService.updateReview(member, movieId, reviewId, reviewDto));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable(name = "movieId") Long movieId, @PathVariable(name = "reviewId") Long reviewId
                                                , @AuthenticationPrincipal(expression = "member") Member member) {
        reviewService.deleteReview(reviewId, member, movieId);
        return ResponseEntity.ok("리뷰가 성공적으로 삭제되었습니다.");
    }

    @GetMapping
    public ResponseEntity<ReviewListDto> getReviewList(@PathVariable(name = "movieId") Long movieId,
                                                       @RequestParam(name = "page", defaultValue = "1") int page,
                                                       @RequestParam(name = "size", defaultValue = "10") int size) {
        ReviewListDto reviewListDto = reviewService.getReviewList(movieId, page, size);
        return ResponseEntity.ok(reviewListDto);
    }
}
