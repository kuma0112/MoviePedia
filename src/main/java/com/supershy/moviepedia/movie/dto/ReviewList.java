package com.supershy.moviepedia.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class ReviewList implements Serializable {
    private Long movieId;
    private String content;
    private String nickname;
}
