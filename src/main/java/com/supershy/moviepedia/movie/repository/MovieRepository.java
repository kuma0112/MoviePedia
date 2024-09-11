package com.supershy.moviepedia.movie.repository;

import com.supershy.moviepedia.movie.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
