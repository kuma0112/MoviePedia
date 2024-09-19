package com.supershy.moviepedia.movie.dto;

import com.supershy.moviepedia.movie.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class MovieDto implements Serializable {

    private Long movieId;
    private String title;
    private String genre;
    private String description;
    private String director;
    private Double reservationRate;
    private String imageUrl;
    private LocalDateTime releaseDate;
    private List<ReviewList> reviewList;  

    public static MovieDto fromEntity(Movie movie, List<ReviewList> reviewList) {
        return MovieDto.builder()
                .movieId(movie.getMovieId())
                .genre(movie.getGenre().getGenreName())  
                .title(movie.getTitle())
                .description(movie.getDescription())
                .director(movie.getDirector())
                .reservationRate(movie.getReservationRate())
                .imageUrl(movie.getImageUrl())
                .releaseDate(movie.getReleaseDate())
                .reviewList(reviewList)  
                .build();
    }
}
