package com.supershy.moviepedia.movie.entity;
import com.supershy.moviepedia.genre.entity.Genre;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.supershy.moviepedia.genre.entity.Genre;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Enumerated(EnumType.STRING)  
    @Column(columnDefinition = "ENUM('NOW_SHOWING', 'UPCOMING')")//jpa에는 string으로 mysql에선 enum으로
    private ReleaseState releaseState;

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
