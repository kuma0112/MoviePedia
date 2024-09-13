package com.supershy.moviepedia.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.supershy.moviepedia.movie.entity.Movie;


public interface MovieRepository extends JpaRepository<Movie, Long> {
}
