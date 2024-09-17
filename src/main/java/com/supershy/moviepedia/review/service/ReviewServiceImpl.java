package com.supershy.moviepedia.review.service;

import com.supershy.moviepedia.member.entity.Member;
import com.supershy.moviepedia.member.repository.MemberRepository;
import com.supershy.moviepedia.movie.entity.Movie;
import com.supershy.moviepedia.movie.repository.MovieRepository;
import com.supershy.moviepedia.review.dto.ReviewDto;
import com.supershy.moviepedia.review.entity.Review;
import com.supershy.moviepedia.review.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final MovieRepository movieRepository;

    @Override
    public void createReview(Member member, Long movieId, ReviewDto reviewDto) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new EntityNotFoundException("찾으시는 영화가 없습니다."));
        Review review = Review.builder()
                .member(member)
                .movie(movie)
                .content(reviewDto.getContent())
                .build();
        reviewRepository.save(review);
    }

    @Override
    public ReviewDto getReview(Long reviewId, Long movieId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("찾으시는 리뷰가 없습니다."));
        if (!review.getMovie().getMovieId().equals(movieId)) {
            throw new IllegalArgumentException("리뷰와 영화 정보가 일치하지 않습니다.");
        } return ReviewDto.fromEntity(review);
    }

    @Override
    public ReviewDto updateReview(Member member, Long movieId, Long reviewId, ReviewDto reviewDto) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("찾으시는 리뷰가 없습니다."));
        if (!review.getMovie().getMovieId().equals(movieId)) {
            throw new IllegalArgumentException("리뷰와 영화 정보가 일치하지 않습니다.");
        }
        if (!review.getMember().getMemberId().equals(member.getMemberId())) {
            throw new IllegalArgumentException("리뷰 작성자와 멤버가 일치하지 않습니다.");
        }
        review.updateContent(reviewDto.getContent());
        reviewRepository.save(review);
        return ReviewDto.fromEntity(review);
    }

    @Override
    public void deleteReview(Long reviewId, Member member, Long movieId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("찾으시는 리뷰가 없습니다."));
        if (!review.getMovie().getMovieId().equals(movieId)) {
            throw new IllegalArgumentException("리뷰와 영화 정보가 일치하지 않습니다.");
        }
        if (!review.getMember().getMemberId().equals(member.getMemberId())) {
            throw new IllegalArgumentException("리뷰 작성자와 멤버가 일치하지 않습니다.");
        }
        reviewRepository.deleteById(reviewId);
    }

    @Override
    public List<ReviewDto> getReviewList(Long movieId) {
        List<Review> reviews = reviewRepository.findAllByMovie_MovieId(movieId);
        return reviews.stream()
                .map(ReviewDto::fromEntity)
                .collect(Collectors.toList());
    }
}
