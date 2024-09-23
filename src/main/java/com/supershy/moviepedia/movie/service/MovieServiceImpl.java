package com.supershy.moviepedia.movie.service;

import com.supershy.moviepedia.movie.dto.MovieDto;
import com.supershy.moviepedia.movie.dto.MovieListDto;
import com.supershy.moviepedia.movie.dto.ReviewList;
import com.supershy.moviepedia.movie.entity.Movie;
import com.supershy.moviepedia.movie.repository.MovieRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable(value = "rankings", key = "T(java.time.LocalDateTime).now().toString()")
    @Override
    public MovieListDto getRanking(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Movie> moviePage = movieRepository.findAllWithReviewsAndGenre(pageable);

        List<MovieDto> movieDtoList = moviePage.stream()
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

        return new MovieListDto(movieDtoList, (int) moviePage.getTotalElements());
    }

    @Override
    public MovieDto findMovieById(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new EntityNotFoundException("찾으시는 영화가 없습니다."));
        List<ReviewList> reviewList = movie.getReviews().stream()
                .map(review -> ReviewList.builder()
                        .content(review.getContent())
                        .nickname(review.getMember().getNickname())
                        .build())
                .collect(Collectors.toList());
        return MovieDto.fromEntity(movie, reviewList);
    }

    @Override
    public MovieListDto getUpcomingMovies(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Movie> moviePage = movieRepository.findUpcomingMovies(pageable);

        List<MovieDto> movieDtoList = moviePage.stream()
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

        return new MovieListDto(movieDtoList, (int) moviePage.getTotalElements());
    }
}
