package com.supershy.moviepedia.movie.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.supershy.moviepedia.movie.dto.MovieDto;

public interface MovieService {
    Page<MovieDto> getRanking(Pageable pageable);
}
