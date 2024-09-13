package com.supershy.moviepedia.like.service;

import com.supershy.moviepedia.like.dto.LikedMovieListDto;

public interface LikeService {

    String insertLike(Long memberId, Long movieId);

    LikedMovieListDto getLikedMovies(int page, int size, Long memberId);
}
