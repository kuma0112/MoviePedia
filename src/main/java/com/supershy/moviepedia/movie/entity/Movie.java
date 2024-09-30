package com.supershy.moviepedia.movie.entity;

import com.supershy.moviepedia.genre.entity.Genre;
import com.supershy.moviepedia.review.entity.Review;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    private String title;
    private String description;
    private String director;
    private Double reservationRate;
    private String imageUrl;
    private LocalDateTime releaseDate;

    @Enumerated(EnumType.STRING)  
    @Column(columnDefinition = "ENUM('NOW_SHOWING', 'UPCOMING')")
    private ReleaseState releaseState;

    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
    @BatchSize(size = 10)
    private List<Review> reviews;

    @Builder
    public Movie(Genre genre, String title, String description, String director, Double reservationRate, String imageUrl, LocalDateTime releaseDate, ReleaseState releaseState) {
        this.genre = genre;
        this.title = title;
        this.description = description;
        this.director = director;
        this.reservationRate = reservationRate;
        this.imageUrl = imageUrl;
        this.releaseDate = releaseDate;
        this.releaseState = releaseState;
    }
}

