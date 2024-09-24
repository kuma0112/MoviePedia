package com.supershy.moviepedia.movie.service;

import com.supershy.moviepedia.movie.dto.MovieDto;
import com.supershy.moviepedia.movie.dto.MovieListDto;

public interface MovieService {
    MovieListDto getRanking(int page, int size);
    MovieListDto getUpcomingMovies(int page, int size);
    MovieDto findMovieById(Long movieId);
    MovieListDto getMoviesBySearchWord(String query, int page, int size);
}