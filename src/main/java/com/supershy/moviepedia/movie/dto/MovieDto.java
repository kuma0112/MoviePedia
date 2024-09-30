package com.supershy.moviepedia.movie.dto;

import com.supershy.moviepedia.movie.entity.Movie;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    @Setter
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

    public MovieDto(Long movieId, String title, String genre, String description, String director, Double reservationRate, String imageUrl, LocalDateTime releaseDate) {
        this.movieId = movieId;
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.director = director;
        this.reservationRate = reservationRate;
        this.imageUrl = imageUrl;
        this.releaseDate = releaseDate;
    }

}
