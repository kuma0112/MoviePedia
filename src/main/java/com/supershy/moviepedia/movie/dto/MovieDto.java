package com.supershy.moviepedia.movie.dto;

import java.time.LocalDateTime;

import com.supershy.moviepedia.genre.entity.Genre;
import com.supershy.moviepedia.movie.entity.Movie;
import com.supershy.moviepedia.movie.entity.ReleaseState;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class MovieDto {

    private Long movieId;
    private Genre genre;
    private String title;
    private String description;
    private String director;
    private Double reservationRate;
    private String imageUrl;
    private LocalDateTime releaseDate;
    private ReleaseState releaseState;  

    public static MovieDto fromEntity(Movie movie) {
        return MovieDto.builder()
                .movieId(movie.getMovieId())
                .genre(movie.getGenre())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .director(movie.getDirector())
                .reservationRate(movie.getReservationRate())
                .imageUrl(movie.getImageUrl())
                .releaseDate(movie.getReleaseDate())
                .releaseState(movie.getReleaseState())
                .build();
    }
}
