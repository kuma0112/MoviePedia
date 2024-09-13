package com.supershy.moviepedia.movie.service;

import com.supershy.moviepedia.movie.dto.MovieListDto;

public interface MovieService {
    MovieListDto getRanking(int page, int size);
}