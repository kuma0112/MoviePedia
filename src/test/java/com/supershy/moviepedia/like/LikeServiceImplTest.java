package com.supershy.moviepedia.like;

import com.supershy.moviepedia.like.dto.LikedMovieListDto;
import com.supershy.moviepedia.like.repository.LikeRepository;
import com.supershy.moviepedia.like.service.LikeServiceImpl;
import com.supershy.moviepedia.member.entity.Member;
import com.supershy.moviepedia.movie.entity.Movie;
import com.supershy.moviepedia.movie.repository.MovieRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LikeServiceImplTest {

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private LikeServiceImpl likeService;

    @Nested
    class insertLike {
        @Test
        void insertLikeTest() {
            // given
            Member member = mock(Member.class);
            Movie movie = mock(Movie.class);
            Long memberId = 1L;
            Long movieId = 2L;
            when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
            when(member.getMemberId()).thenReturn(memberId);
            when(likeRepository.existsByMember_MemberIdAndMovie_MovieId(memberId, movieId)).thenReturn(false);

            // when
            String result = likeService.insertLike(member, movieId);

            // then
            assertThat(result).isEqualTo("좋아요가 등록되었습니다.");
        }
    }

    @Test
    void deleteLikeTest() {
        // given
        Member member = mock(Member.class);
        Movie movie = mock(Movie.class);
        Long memberId = 1L;
        Long movieId = 2L;
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        when(member.getMemberId()).thenReturn(memberId);
        when(likeRepository.existsByMember_MemberIdAndMovie_MovieId(memberId, movieId)).thenReturn(true);

        // when
        String result = likeService.insertLike(member, movieId);

        // then
        assertThat(result).isEqualTo("좋아요가 취소되었습니다.");
    }

    @Nested
    class getLikedMovies {

        @Test
        void getLikedMoviesTest() {
            // given
            int page = 0;
            int size = 10;
            Long memberId = 1L;

            Movie movie1 = Movie.builder()
                    .title("제목1")
                    .releaseDate(LocalDateTime.of(2024, 5, 2, 10, 0))
                    .imageUrl("url1")
                    .reservationRate(22.00)
                    .build();

            Movie movie2 = Movie.builder()
                    .title("제목2")
                    .releaseDate(LocalDateTime.of(2023, 5, 2, 10, 0))
                    .imageUrl("url2")
                    .reservationRate(20.00)
                    .build();

            List<Movie> movies = List.of(movie1, movie2);
            Sort sortObj =  Sort.by(Sort.Direction.ASC, "title");
            Pageable pageable = PageRequest.of(page, size, sortObj);
            Page<Movie> pageMovies = new PageImpl<>(movies, pageable, movies.size());

            when(likeRepository.findAllByMember_MemberId(memberId, pageable)).thenReturn(pageMovies);

            // when
            LikedMovieListDto result = likeService.getLikedMovies(page, size, memberId);

            // then
            assertThat(result).isNotNull();
            assertThat(result.getMovieList()).isNotNull();
            assertThat(result.getMovieList().size()).isEqualTo(2);
            assertThat(result.getMovieList().get(0).getTitle()).isEqualTo(movie1.getTitle());
            assertThat(result.getMovieList().get(1).getTitle()).isEqualTo(movie2.getTitle());
            assertThat(result.getTotalElements()).isEqualTo(2);
        }
    }
}


