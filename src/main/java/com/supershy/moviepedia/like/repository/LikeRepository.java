package com.supershy.moviepedia.like.repository;

import com.supershy.moviepedia.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
