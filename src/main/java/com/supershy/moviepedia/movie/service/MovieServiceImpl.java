package com.supershy.moviepedia.movie.service;

import com.supershy.moviepedia.movie.dto.MovieDto;
import com.supershy.moviepedia.movie.dto.ReviewList;
import com.supershy.moviepedia.movie.dto.MovieListDto;
import com.supershy.moviepedia.movie.entity.Movie;
import com.supershy.moviepedia.movie.repository.MovieRepository;
import com.supershy.moviepedia.review.entity.Review;
import com.supershy.moviepedia.review.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public MovieListDto getRanking(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Movie> moviePage = movieRepository.findAll(pageable);

        List<MovieDto> movieDtoList = moviePage.stream()
            .map(movie -> {
                // 영화에 맞는 리뷰 리스트를 가져옴
                List<Review> reviews = reviewRepository.findByMovie(movie);

                // 리뷰에서 content와 nickname을 추출하여 ReviewList로 변환
                List<ReviewList> reviewList = reviews.stream()
                    .map(review -> ReviewList.builder()
                        .content(review.getContent())
                        .nickname(review.getMember().getNickname())
                        .build())
                    .collect(Collectors.toList());

                return MovieDto.fromEntity(movie, reviewList);
            })
            .collect(Collectors.toList());

        return new MovieListDto(movieDtoList, (int) moviePage.getTotalElements());
    }
}
