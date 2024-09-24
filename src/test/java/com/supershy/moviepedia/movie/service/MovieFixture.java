package com.supershy.moviepedia.movie.service;

import com.supershy.moviepedia.genre.entity.Genre;
import com.supershy.moviepedia.member.entity.Member;
import com.supershy.moviepedia.movie.dto.MovieDto;
import com.supershy.moviepedia.movie.dto.ReviewList;
import com.supershy.moviepedia.movie.entity.Movie;
import com.supershy.moviepedia.review.entity.Review;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MovieFixture {

    // Mock Movie 리스트 생성
    public static List<Movie> createMockMovieList() {
        Movie movie1 = mock(Movie.class);
        Movie movie2 = mock(Movie.class);

        // Mock 장르 및 리뷰 설정
        Genre genre1 = mock(Genre.class);
        Genre genre2 = mock(Genre.class);
        when(genre1.getGenreName()).thenReturn("Action");
        when(genre2.getGenreName()).thenReturn("Drama");
        when(movie1.getGenre()).thenReturn(genre1);
        when(movie2.getGenre()).thenReturn(genre2);

        Review review1 = mock(Review.class);
        Review review2 = mock(Review.class);
        Member member1 = mock(Member.class);
        Member member2 = mock(Member.class);
        when(member1.getNickname()).thenReturn("jiyun1");
        when(member2.getNickname()).thenReturn("jiyun2");
        when(review1.getMember()).thenReturn(member1);
        when(review2.getMember()).thenReturn(member2);
        when(review1.getContent()).thenReturn("content1");
        when(review2.getContent()).thenReturn("content2");

        when(movie1.getReviews()).thenReturn(List.of(review1, review2));
        when(movie2.getReviews()).thenReturn(List.of(review1, review2));

        return List.of(movie1, movie2);
    }

    // Mock 개봉 예정 Movie 리스트 생성
    public static List<Movie> createMockUpcomingMovieList() {
        Movie upcomingMovie1 = mock(Movie.class);
        Movie upcomingMovie2 = mock(Movie.class);

        // Mock 장르 설정
        Genre genre1 = mock(Genre.class);
        Genre genre2 = mock(Genre.class);
        when(genre1.getGenreName()).thenReturn("Action");
        when(genre2.getGenreName()).thenReturn("Drama");
        when(upcomingMovie1.getGenre()).thenReturn(genre1);
        when(upcomingMovie2.getGenre()).thenReturn(genre2);

        // 리뷰는 없음
        when(upcomingMovie1.getReviews()).thenReturn(List.of());
        when(upcomingMovie2.getReviews()).thenReturn(List.of());

        return List.of(upcomingMovie1, upcomingMovie2);
    }

    // MovieDto 리스트 생성
    public static List<MovieDto> createExpectedMovieDtoList(List<Movie> movieList) {
        return movieList.stream()
            .map(movie -> {
                List<ReviewList> reviewList = movie.getReviews().stream()
                    .map(review -> ReviewList.builder()
                        .content(review.getContent())
                        .nickname(review.getMember().getNickname())
                        .build())
                    .toList();
                return MovieDto.fromEntity(movie, reviewList);
            })
            .toList();
    }
}
