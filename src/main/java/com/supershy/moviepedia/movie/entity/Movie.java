package com.supershy.moviepedia.movie.entity;

import com.supershy.moviepedia.genre.entity.Genre;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;

    @OneToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    private String title;

    private String description;

    private String director;

    private Double reservationRate;

    private String imageUrl;

    private LocalDateTime releaseDate;
}
