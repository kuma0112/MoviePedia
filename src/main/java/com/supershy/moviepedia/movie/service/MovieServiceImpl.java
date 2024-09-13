package com.supershy.moviepedia.movie.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.supershy.moviepedia.movie.dto.MovieDto;
import com.supershy.moviepedia.movie.repository.MovieRepository;

import lombok.RequiredArgsConstructor;

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
}
