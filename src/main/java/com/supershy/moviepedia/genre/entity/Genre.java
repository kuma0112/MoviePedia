package com.supershy.moviepedia.genre.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long genreId;

    @Column(nullable = false)
    private String genreName;
}
