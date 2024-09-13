package com.supershy.moviepedia.movie.repository;

import com.supershy.moviepedia.movie.entity.Movie;

import java.util.List;

public interface QueryDslMovieRepository {

    public List<Movie> getMoviesBySearchWord(String searchWord);
}
