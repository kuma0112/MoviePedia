package com.supershy.moviepedia.common.controller;

import com.supershy.moviepedia.movie.dto.MovieDto;
import com.supershy.moviepedia.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class PageController {
    private final MovieService movieService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/pages/register")
    public String register() {
        return "register";
    }

    @GetMapping("/pages/login")
    public String login() {
        return "login";
    }

    @GetMapping("/pages/movies/{movieId}")
    public String detailMovie(@PathVariable("movieId") Long movieId, Model model) {
        MovieDto movie = movieService.findMovieById(movieId);
        model.addAttribute("movie", movie);
        return "detailMovie";
    }

    @GetMapping("/pages/mypage")
    public String getMyPage() {
        return "mypage";
    }
}
