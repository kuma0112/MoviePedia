package com.supershy.moviepedia.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReviewListDto {
    List<ReviewDto> reviewList;
    int totalElements;
}
