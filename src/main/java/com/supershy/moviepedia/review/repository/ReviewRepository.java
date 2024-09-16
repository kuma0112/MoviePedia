package com.supershy.moviepedia.review.repository;

import com.supershy.moviepedia.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findById(Long reviewId);

    List<Review> findAllByMovie_MovieId(Long movieId);

}
