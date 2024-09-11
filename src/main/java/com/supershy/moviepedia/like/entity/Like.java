package com.supershy.moviepedia.like.entity;

import com.supershy.moviepedia.like.entity.id.LikeId;
import com.supershy.moviepedia.member.entity.Member;
import com.supershy.moviepedia.movie.entity.Movie;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Like {

    @EmbeddedId
    private LikeId likeId;

    @ManyToOne
    @MapsId("memberId")
    @JoinColumn(nullable = false)
    private Member member;

    @ManyToOne
    @MapsId("movieId")
    @JoinColumn(nullable = false)
    private Movie movie;
}
