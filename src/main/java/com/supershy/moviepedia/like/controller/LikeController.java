package com.supershy.moviepedia.like.controller;

import com.supershy.moviepedia.like.service.LikeService;
import com.supershy.moviepedia.member.entity.Member;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movies")
@AllArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{movieId}/likes")
    public ResponseEntity<?> insertLike(@PathVariable("movieId") Long movieId,
                                        @AuthenticationPrincipal(expression = "member") Member member) {
        return ResponseEntity.status(HttpStatus.OK).body(likeService.insertLike(member, movieId));
    }

    @GetMapping("/likes")
    public ResponseEntity<?> getLikedMovies(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
                                            @AuthenticationPrincipal(expression = "member") Member member) {
        return ResponseEntity.status(HttpStatus.OK).body(likeService.getLikedMovies(page, size, member.getMemberId()));
    }
}

