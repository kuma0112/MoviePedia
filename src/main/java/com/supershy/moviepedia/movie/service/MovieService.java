package com.supershy.moviepedia.movie.service;

import com.supershy.moviepedia.movie.dto.MovieDto;
import com.supershy.moviepedia.movie.dto.MovieListDto;
import com.supershy.moviepedia.movie.entity.Movie;

public interface MovieService {
    MovieListDto getRanking(int page, int size);

    MovieDto findMovieById(Long movieId);
}