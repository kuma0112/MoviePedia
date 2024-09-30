package com.supershy.moviepedia.review.repository;

import com.supershy.moviepedia.movie.dto.ReviewList;
import com.supershy.moviepedia.review.dto.ReviewDto;
import com.supershy.moviepedia.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findById(Long reviewId);

    @Query("SELECT r FROM Review r " +
            "INNER JOIN FETCH r.movie m " +
            "INNER JOIN FETCH r.member mem " +
            "WHERE m.movieId = :movieId")
    Page<Review> findAllReviewsWithMovieAndMember(@Param("movieId") Long movieId, Pageable pageable);

    List<Review> findAllByMember_MemberId(Long memberId);

    @Query("SELECT new com.supershy.moviepedia.movie.dto.ReviewList(r.movie.movieId, r.content, r.member.nickname) " +
            "FROM Review r WHERE r.movie.movieId IN :movieIds")
    List<ReviewList> findReviewsByMovieIds(List<Long> movieIds);
}
