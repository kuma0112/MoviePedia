package com.supershy.moviepedia.movie.entity;

import com.supershy.moviepedia.genre.entity.Genre;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    private String title;

    private String description;

    private String director;

    private BigDecimal reservationRate;

    private String imageUrl;

    private LocalDateTime releaseDate;
}
