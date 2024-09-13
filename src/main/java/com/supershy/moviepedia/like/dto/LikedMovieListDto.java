package com.supershy.moviepedia.like.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class LikedMovieListDto {
    List<LikedMovieDto> movieList;
    private int totalElements;

    public LikedMovieListDto (List<LikedMovieDto> movieList, int totalElements) {
        this.movieList = movieList;
        this.totalElements = totalElements;
    }
}
