package com.supershy.moviepedia.movie.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.supershy.moviepedia.movie.dto.MovieDto;

import java.util.List;

public interface MovieService {
    Page<MovieDto> getRanking(Pageable pageable);

    List<MovieDto> getMoviesBySearchWord(String searchWord);

}
