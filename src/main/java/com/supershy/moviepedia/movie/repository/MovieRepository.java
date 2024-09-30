package com.supershy.moviepedia.movie.repository;

import com.supershy.moviepedia.movie.dto.MovieDto;
import com.supershy.moviepedia.movie.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long>, QueryDslMovieRepository {

    @Query(value = "SELECT DISTINCT m FROM Movie m LEFT JOIN FETCH m.genre LEFT JOIN FETCH m.reviews r LEFT JOIN FETCH r.member",
            countQuery = "SELECT COUNT(m) FROM Movie m")
    Page<Movie> findAllWithReviewsAndGenre(Pageable pageable);

    @Query(value = "SELECT DISTINCT m FROM Movie m LEFT JOIN FETCH m.genre LEFT JOIN FETCH m.reviews r LEFT JOIN FETCH r.member WHERE m.movieId = :movieId")
    Optional<Movie> findById(@Param("movieId") Long movieId);

    @Query(value = "SELECT DISTINCT m FROM Movie m LEFT JOIN FETCH m.genre LEFT JOIN FETCH m.reviews r LEFT JOIN FETCH r.member WHERE m.releaseState = 'UPCOMING'",
           countQuery = "SELECT COUNT(m) FROM Movie m WHERE m.releaseState = 'UPCOMING'")
    Page<Movie> findUpcomingMovies(Pageable pageable);

    @Query("SELECT new com.supershy.moviepedia.movie.dto.MovieDto(m.movieId, m.title, m.genre.genreName, m.description, m.director, m.reservationRate, m.imageUrl, m.releaseDate) " +
            "FROM Movie m")
    Page<MovieDto> findMovies(Pageable pageable);

}
