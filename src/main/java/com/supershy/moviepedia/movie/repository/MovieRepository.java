package com.supershy.moviepedia.movie.repository;

import com.supershy.moviepedia.movie.entity.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MovieRepository extends CrudRepository<Movie, Long> {

    @Query("SELECT m FROM Movie m JOIN FETCH m.reviews")
    List<Movie> findAllWithReviews();
}
