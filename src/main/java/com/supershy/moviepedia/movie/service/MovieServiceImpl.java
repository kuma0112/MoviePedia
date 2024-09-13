package com.supershy.moviepedia.movie.service;

import com.supershy.moviepedia.movie.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.supershy.moviepedia.movie.dto.MovieDto;
import com.supershy.moviepedia.movie.repository.MovieRepository;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public Page<MovieDto> getRanking(Pageable pageable) {
        Pageable sortedByReservationRate = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "reservationRate"));
        return movieRepository.findAll(sortedByReservationRate)
                .map(MovieDto::fromEntity);
    }

    @Override
    public List<MovieDto> getMoviesBySearchWord(String searchWord) {
        List<Movie> movieList = movieRepository.getMoviesBySearchWord(searchWord);
        List<MovieDto> movieDtoList = new ArrayList<>();
        for (Movie movie : movieList) {
            MovieDto movieDto = MovieDto.fromEntity(movie);
            movieDtoList.add(movieDto);
        }
        return movieDtoList;
    }
}
