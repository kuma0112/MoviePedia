package com.supershy.moviepedia.movie.service;

import com.supershy.moviepedia.movie.dto.MovieDto;
import com.supershy.moviepedia.movie.dto.ReviewList;
import com.supershy.moviepedia.movie.dto.MovieListDto;
import com.supershy.moviepedia.movie.entity.Movie;
import com.supershy.moviepedia.movie.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public MovieListDto getRanking(int page, int size) {
        List<Movie> movies = movieRepository.findAllWithReviews();

        List<MovieDto> movieDtoList = movies.stream()
            .map(movie -> {
                List<ReviewList> reviewList = movie.getReviews().stream()
                    .map(review -> ReviewList.builder()
                        .content(review.getContent())
                        .nickname(review.getMember().getNickname())
                        .build())
                    .collect(Collectors.toList());

                return MovieDto.fromEntity(movie, reviewList);
            })
            .collect(Collectors.toList());

        return new MovieListDto(movieDtoList, movieDtoList.size());
    }
}
