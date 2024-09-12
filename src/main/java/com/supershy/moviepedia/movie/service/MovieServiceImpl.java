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
        // 예매율 기준으로 내림차순 정렬
        Pageable sortedByReservationRate = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "reservationRate"));
        //sort객체 따로만들기
        
        return movieRepository.findAll(sortedByReservationRate)
                .map(movie -> MovieDto.fromEntity(movie));
    }
}
