package com.supershy.moviepedia.review.repository;

import com.supershy.moviepedia.movie.entity.Movie;
import com.supershy.moviepedia.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByMovie(Movie movie);
}
