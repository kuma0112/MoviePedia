package com.supershy.moviepedia.like.controller;

import com.supershy.moviepedia.like.service.LikeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movies")
@AllArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{movieId}/likes")
    public ResponseEntity<?> insertLike(@PathVariable("movieId") Long movieId) {
        Long memberId = 1L;
        return ResponseEntity.status(HttpStatus.OK).body(likeService.insertLike(memberId, movieId));
    }
}
