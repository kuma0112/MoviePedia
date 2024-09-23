package com.supershy.moviepedia.like.service;

import com.supershy.moviepedia.like.dto.LikedMovieListDto;
import com.supershy.moviepedia.member.entity.Member;

public interface LikeService {

    String insertLike(Member member, Long movieId);

    LikedMovieListDto getLikedMovies(int page, int size, Long memberId);
}
