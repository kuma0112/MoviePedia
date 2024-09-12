package com.supershy.moviepedia.movie.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supershy.moviepedia.movie.dto.MovieDto;
import com.supershy.moviepedia.movie.service.MovieService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    //responseEntity
    @GetMapping("/rankings")
    public Page<MovieDto> getRanking(Pageable pageable){
    	return movieService.getRanking(pageable);
    }
}
