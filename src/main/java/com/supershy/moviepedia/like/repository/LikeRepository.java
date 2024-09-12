package com.supershy.moviepedia.like.repository;

import com.supershy.moviepedia.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByMember_MemberIdAndMovie_MovieId(Long memberId, Long movieId);

    void deleteByMember_MemberIdAndMovie_MovieId(Long memberId, Long movieId);
}
