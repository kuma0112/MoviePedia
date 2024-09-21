package com.supershy.moviepedia.member.dto;

import com.supershy.moviepedia.movie.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MyMovieDto {
    private Long movieId;
    private String title;
    private String imageUrl;
    private LocalDateTime releaseDate;

    public static MyMovieDto fromEntity(Movie movie) {
        return new MyMovieDto(
                movie.getMovieId(),
                movie.getTitle(),
                movie.getImageUrl(),
                movie.getReleaseDate());
    }
}
