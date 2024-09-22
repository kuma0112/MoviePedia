package com.supershy.moviepedia;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.supershy.moviepedia.genre.entity.Genre;
import com.supershy.moviepedia.member.entity.Member;
import com.supershy.moviepedia.movie.dto.MovieDto;
import com.supershy.moviepedia.movie.dto.MovieListDto;
import com.supershy.moviepedia.movie.dto.ReviewList;
import com.supershy.moviepedia.movie.entity.Movie;
import com.supershy.moviepedia.movie.repository.MovieRepository;
import com.supershy.moviepedia.movie.service.MovieServiceImpl;
import com.supershy.moviepedia.review.entity.Review;
import com.supershy.moviepedia.review.repository.ReviewRepository;

@ExtendWith(MockitoExtension.class)
class MovieServiceImplTest {
	//mock 객체생성
    @Mock
    private MovieRepository movieRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    @Test //jUnit test임!
    void getRankingTest() {
        // given
        int page = 0;
        int size = 10;

        Pageable pageable = PageRequest.of(page, size); //페이지번호 크기
        
        //가짜만들기
        Movie movie1 = mock(Movie.class);
        Movie movie2 = mock(Movie.class);
        
        List<Movie> movieList = List.of(movie1, movie2);

        Page<Movie> moviePage = new PageImpl<>(movieList, pageable, movieList.size());
        when(movieRepository.findAllWithReviewsAndGenre(pageable)).thenReturn(moviePage);
        //movie column연결
        // Mock Genre 객체 생성
        Genre genre1 = mock(Genre.class);
        Genre genre2 = mock(Genre.class);

        // Genre 객체
        when(genre1.getGenreName()).thenReturn("Action");
        when(genre2.getGenreName()).thenReturn("Drama");

        //Mock Genre 설정
        when(movie1.getGenre()).thenReturn(genre1);
        when(movie2.getGenre()).thenReturn(genre2);

        for (Movie movie : movieList) {
            Review review1 = mock(Review.class);
            Review review2 = mock(Review.class);
            List<Review> reviews = List.of(review1, review2);

            Member member1 = mock(Member.class); // Member 객체를 Mock으로 생성
            Member member2 = mock(Member.class); // Member 객체를 Mock으로 생성

            // Membe
            when(member1.getNickname()).thenReturn("nickname1");
            when(member2.getNickname()).thenReturn("nickname2");

            // Review
            when(review1.getMember()).thenReturn(member1);
            when(review2.getMember()).thenReturn(member2);

            when(movie.getReviews()).thenReturn(reviews);
            when(review1.getContent()).thenReturn("content1");
            when(review2.getContent()).thenReturn("content2");
        }

        List<MovieDto> expectedMovieDtoList = movieList.stream()
            .map(movie -> {
                List<ReviewList> reviewList = movie.getReviews().stream()
                    .map(review -> ReviewList.builder()
                        .content(review.getContent())
                        .nickname(review.getMember().getNickname()) // 여기서 getMember null이 아니게
                        .build())
                    .toList();
                return MovieDto.fromEntity(movie, reviewList);
            })
            .toList();

        MovieListDto expectedMovieListDto = new MovieListDto(expectedMovieDtoList, movieList.size());

        // when
        MovieListDto result = movieService.getRanking(page, size);

        // then
        assertAll(
            () -> assertThat(result.getMovieList()).hasSize(movieList.size()),
            () -> assertThat(result.getMovieList()).usingRecursiveComparison().isEqualTo(expectedMovieListDto.getMovieList())
        );
    }
}