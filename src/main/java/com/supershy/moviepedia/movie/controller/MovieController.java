package com.supershy.moviepedia.movie.controller;

import com.supershy.moviepedia.movie.dto.MovieListDto;
import com.supershy.moviepedia.movie.service.MovieService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/rankings")
    public ResponseEntity<?> getRanking(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        MovieListDto movieRanking = movieService.getRanking(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(movieRanking);  // 영화 리스트와 총 개수를 반환
    }


    @GetMapping("/upcoming")
    public ResponseEntity<?> getUpcomingMovies(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        MovieListDto upcomingMovies = movieService.getUpcomingMovies(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(upcomingMovies);  // 상영 예정 영화 리스트와 총 개수를 반환
    }

    @GetMapping("/searches")
    public ResponseEntity<?> getMoviesBySearchWord(@RequestParam(name = "query") String query,
                                                   @RequestParam(name = "page", defaultValue = "0") int page,
                                                   @RequestParam(name = "size", defaultValue = "10") int size) {
        MovieListDto searchMovies = movieService.getMoviesBySearchWord(query, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(searchMovies);
    }
}