package com.supershy.moviepedia.review.entity;

import com.supershy.moviepedia.member.entity.Member;
import com.supershy.moviepedia.movie.entity.Movie;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Column(nullable = false)
    private String content;
}
