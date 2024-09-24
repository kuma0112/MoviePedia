package com.supershy.moviepedia.movie.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
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

import com.supershy.moviepedia.movie.dto.MovieDto;
import com.supershy.moviepedia.movie.dto.MovieListDto;
import com.supershy.moviepedia.movie.entity.Movie;
import com.supershy.moviepedia.movie.repository.MovieRepository;


@ExtendWith(MockitoExtension.class)
class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    @Test
    void getRankingTest() {
        // given
        int page = 0;
        int size = 10;

        Pageable pageable = PageRequest.of(page, size);

        List<Movie> movieList = MovieFixture.createMockMovieList(); // Fixture 사용
        Page<Movie> moviePage = new PageImpl<>(movieList, pageable, movieList.size());
        when(movieRepository.findAllWithReviewsAndGenre(pageable)).thenReturn(moviePage);

        List<MovieDto> expectedMovieDtoList = MovieFixture.createExpectedMovieDtoList(movieList); // Fixture 사용
        MovieListDto expectedMovieListDto = new MovieListDto(expectedMovieDtoList, movieList.size());

        // when
        MovieListDto result = movieService.getRanking(page, size);

        // then
        assertAll(
            () -> assertThat(result.getMovieList()).hasSize(movieList.size()),
            () -> {
                for (int i = 0; i < movieList.size(); i++) {
                    MovieDto resultMovie = result.getMovieList().get(i);
                    MovieDto expectedMovie = expectedMovieListDto.getMovieList().get(i);

                    // 각 필드를 명시적으로 비교
                    assertThat(resultMovie.getTitle()).isEqualTo(expectedMovie.getTitle());
                    assertThat(resultMovie.getGenre()).isEqualTo(expectedMovie.getGenre());
                    assertThat(resultMovie.getReviewList()).hasSize(expectedMovie.getReviewList().size());//애는 리뷰 갯수만 필요
                }
            }
        );
    }

    @Test
    void getUpcomingTest() {
        // given
        int page = 0;
        int size = 5;

        Pageable pageable = PageRequest.of(page, size);

        List<Movie> upcomingMovieList = MovieFixture.createMockUpcomingMovieList(); // Fixture 사용
        Page<Movie> upcomingMoviePage = new PageImpl<>(upcomingMovieList, pageable, upcomingMovieList.size());
        when(movieRepository.findUpcomingMovies(pageable)).thenReturn(upcomingMoviePage);

        List<MovieDto> expectedMovieDtoList = MovieFixture.createExpectedMovieDtoList(upcomingMovieList); // Fixture 사용
        MovieListDto expectedMovieListDto = new MovieListDto(expectedMovieDtoList, upcomingMovieList.size());

        // when
        MovieListDto result = movieService.getUpcomingMovies(page, size);

        // then
        assertAll(
            () -> assertThat(result.getMovieList()).hasSize(upcomingMovieList.size()),
            () -> {
                for (int i = 0; i < upcomingMovieList.size(); i++) {
                    MovieDto resultMovie = result.getMovieList().get(i);
                    MovieDto expectedMovie = expectedMovieListDto.getMovieList().get(i);

                    
                    assertThat(resultMovie.getTitle()).isEqualTo(expectedMovie.getTitle());
                    assertThat(resultMovie.getGenre()).isEqualTo(expectedMovie.getGenre());
                    assertThat(resultMovie.getReviewList()).hasSize(expectedMovie.getReviewList().size());
                }
            }
        );
    }
}
