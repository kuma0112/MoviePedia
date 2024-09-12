package com.supershy.moviepedia.like.repository;

import com.supershy.moviepedia.like.entity.Like;
import com.supershy.moviepedia.like.entity.id.LikeId;
import com.supershy.moviepedia.movie.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LikeRepository extends JpaRepository<Like, LikeId> {
    boolean existsByMember_MemberIdAndMovie_MovieId(Long memberId, Long movieId);

    void deleteByMember_MemberIdAndMovie_MovieId(Long memberId, Long movieId);

    @Query(value = "select m from Movie m join Like l on l.movie.movieId = m.movieId where l.member.memberId = :memberId",
            countQuery = "select count(m) from Movie m join Like l on l.movie.movieId = m.movieId where l.member.memberId = :memberId")
    Page<Movie> findAllByMember_MemberId(Long memberId, Pageable pageable);
}
