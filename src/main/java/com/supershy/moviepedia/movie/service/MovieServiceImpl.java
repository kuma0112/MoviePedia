package com.supershy.moviepedia.movie.service;

import com.supershy.moviepedia.movie.dto.MovieDto;
import com.supershy.moviepedia.movie.dto.MovieListDto;
import com.supershy.moviepedia.movie.dto.ReviewList;
import com.supershy.moviepedia.movie.entity.Movie;
import com.supershy.moviepedia.movie.repository.MovieRepository;
import com.supershy.moviepedia.review.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;

    @Cacheable(value = "rankings", key = "#page + '-' + #size")
    @Override
    public MovieListDto getRanking(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<MovieDto> movieDtos = movieRepository.findMovies(pageable);

        List<Long> movieIds = movieDtos.stream()
                .map(MovieDto::getMovieId)
                .collect(Collectors.toList());

        List<ReviewList> reviews = reviewRepository.findReviewsByMovieIds(movieIds);

        movieDtos.forEach(movieDto -> {
            List<ReviewList> movieReviews = reviews.stream()
                    .filter(review -> review.getMovieId().equals(movieDto.getMovieId()))
                    .collect(Collectors.toList());
            movieDto.setReviewList(movieReviews);
        });

        return new MovieListDto(movieDtos.getContent(), (int) movieDtos.getTotalElements());
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
    public MovieListDto getMoviesBySearchWord(String query, int page, int size) {
        List<Movie> movies = movieRepository.getMoviesBySearchWord(query, page, size);

        List<MovieDto> movieDtos = movies.stream()
                .map(movie -> MovieDto.builder()
                        .movieId(movie.getMovieId())
                        .title(movie.getTitle())
                        .director(movie.getDirector())
                        .genre(movie.getGenre().getGenreName())
                        .description(movie.getDescription())
                        .releaseDate(movie.getReleaseDate())
                        .imageUrl(movie.getImageUrl())
                        .reservationRate(movie.getReservationRate())
                        .build())
                .collect(Collectors.toList());

        return MovieListDto.builder()
                .movieList(movieDtos)
                .build();
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
