package com.supershy.moviepedia.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class MovieListDto {
    private List<MovieDto> movieList;
    private int totalElements;
}
