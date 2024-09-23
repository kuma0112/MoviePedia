package com.supershy.moviepedia.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class MovieListDto implements Serializable {
    private List<MovieDto> movieList;
    private int totalElements;
}
