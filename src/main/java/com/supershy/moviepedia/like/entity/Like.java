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
@Table(name = "likes")
public class Like {

    @EmbeddedId
    private LikeId likeId;

    @ManyToOne
    @MapsId("memberId")
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @MapsId("movieId")
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    public Like(Member member, Movie movie) {
        this.likeId = new LikeId(member.getMemberId(), movie.getMovieId());
        this.member = member;
        this.movie = movie;
    }
}
