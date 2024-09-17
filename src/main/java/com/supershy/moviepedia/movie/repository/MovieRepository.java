package com.supershy.moviepedia.movie.repository;

import com.supershy.moviepedia.movie.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MovieRepository extends CrudRepository<Movie, Long> {

    @Query(value = "SELECT DISTINCT m FROM Movie m LEFT JOIN FETCH m.genre LEFT JOIN FETCH m.reviews", 
           countQuery = "SELECT COUNT(m) FROM Movie m")
    Page<Movie> findAllWithReviewsAndGenre(Pageable pageable);

    @Query(value = "SELECT DISTINCT m FROM Movie m JOIN FETCH m.genre JOIN FETCH m.reviews r JOIN FETCH r.member WHERE m.movieId = :movieId")
    Optional<Movie> findById(Long movieId);
}
