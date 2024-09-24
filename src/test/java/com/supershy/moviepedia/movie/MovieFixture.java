package com.supershy.moviepedia.movie;

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

    private MovieFixture() {}

    // 단일 Movie 객체 생성
    public static Movie createMockMovie(String genreName, List<Review> reviews) {
        Movie movie = mock(Movie.class);
        Genre genre = createMockGenre(genreName);

        when(movie.getGenre()).thenReturn(genre);
        when(movie.getReviews()).thenReturn(reviews);

        return movie;
    }

    // Mock 장르 객체 생성
    private static Genre createMockGenre(String genreName) {
        Genre genre = mock(Genre.class);
        when(genre.getGenreName()).thenReturn(genreName);
        return genre;
    }

    // Mock 리뷰 리스트 생성
    public static List<Review> createMockReviewList(String... nicknames) {
        return List.of(nicknames).stream()
            .map(nickname -> createMockReview(nickname, "content for " + nickname))
            .toList();
    }

    // 단일 Review 객체 생성
    private static Review createMockReview(String nickname, String content) {
        Review review = mock(Review.class);
        Member member = createMockMember(nickname);
        when(review.getMember()).thenReturn(member);
        when(review.getContent()).thenReturn(content);
        return review;
    }

    // 단일 Member 객체 생성
    private static Member createMockMember(String nickname) {
        Member member = mock(Member.class);
        when(member.getNickname()).thenReturn(nickname);
        return member;
    }

    // Mock Movie 리스트 생성
    public static List<Movie> createMockMovieList() {
        List<Review> reviews = createMockReviewList("jiyun1", "jiyun2");
        Movie movie1 = createMockMovie("Action", reviews);
        Movie movie2 = createMockMovie("Drama", reviews);

        return List.of(movie1, movie2);
    }

    // Mock 개봉 예정 Movie 리스트 생성
    public static List<Movie> createMockUpcomingMovieList() {
        Movie upcomingMovie1 = createMockMovie("Action", List.of());
        Movie upcomingMovie2 = createMockMovie("Drama", List.of());

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
