package com.supershy.moviepedia.movie.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import com.supershy.moviepedia.movie.dto.MovieListDto;
import com.supershy.moviepedia.movie.service.MovieService;

@WebMvcTest(MovieController.class)//service, repository와같은 다른계층의 bean들 로드 x -> controller만 가지고 진행
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;  // http를 직접보내지않고 컨트롤러 테스트.

    @MockBean
    private MovieService movieService;

    @Nested
    class GetRanking {

        @Test
        @WithMockUser(username = "testUser", roles = {"USER"})  
        void 랭킹_조회() throws Exception {
            // given
            MovieListDto mockMovieListDto = new MovieListDto(List.of(), 2);  
            when(movieService.getRanking(0, 10)).thenReturn(mockMovieListDto);

            // when
            mockMvc.perform(get("/api/movies/rankings")
                            .param("page", "0")
                            .param("size", "10")
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8"))
                    // then
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.totalElements").value(2));
        }
    }

    @Nested
    class GetUpcomingMovies {	

        @Test
        @WithMockUser(username = "testUser", roles = {"USER"})  
        void 영화_조회() throws Exception {
            // given
            MovieListDto mockUpcomingMovies = new MovieListDto(List.of(), 2);  
            when(movieService.getUpcomingMovies(0, 10)).thenReturn(mockUpcomingMovies);

            // when
            mockMvc.perform(get("/api/movies/upcoming")
                            .param("page", "0")
                            .param("size", "10")
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8"))
                    // then
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.totalElements").value(2));
        }
    }

    @Nested
    class GetMoviesBySearchWord {

        @Test
        @WithMockUser(username = "testUser", roles = {"USER"})  
        void 영화_검색() throws Exception {
            // given
            MovieListDto mockSearchMovies = new MovieListDto(List.of(), 1);  
            when(movieService.getMoviesBySearchWord("abc", 0, 10)).thenReturn(mockSearchMovies);

            // when
            mockMvc.perform(get("/api/movies/searches")
                            .param("query", "abc")
                            .param("page", "0")
                            .param("size", "10")
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8"))
                    // then
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.totalElements").value(1));
        }
    }
}
