package com.supershy.moviepedia.like.dto;

import com.supershy.moviepedia.movie.entity.Movie;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LikedMovieDto {
    private String title;
    private Double reservationRate;
    private String imageUrl;
    private LocalDateTime releaseDate;

    public LikedMovieDto(Movie movie) {
        this.title = movie.getTitle();
        this.reservationRate = movie.getReservationRate();
        this.imageUrl = movie.getImageUrl();
        this.releaseDate = movie.getReleaseDate();
    }
}
