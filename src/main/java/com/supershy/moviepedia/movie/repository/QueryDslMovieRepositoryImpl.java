package com.supershy.moviepedia.movie.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.supershy.moviepedia.movie.entity.Movie;
import com.supershy.moviepedia.movie.entity.QMovie;
import lombok.AllArgsConstructor;

import java.util.List;

import static com.supershy.moviepedia.movie.entity.QMovie.movie;

@AllArgsConstructor
public class QueryDslMovieRepositoryImpl implements QueryDslMovieRepository {

    private JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Movie> getMoviesBySearchWord(String searchWord) {

        QMovie movie = QMovie.movie;
        return jpaQueryFactory
                .select(movie)
                .from(movie)
                .where(
                        containsKeywordInTitle(searchWord)
                                .or(containsKeywordInDirector(searchWord))
                                .or(containsKeywordInGenre(searchWord))
                )
                .fetch();
    }

    private BooleanExpression containsKeywordInTitle(String searchWord) {
        return searchWord != null ? movie.title.containsIgnoreCase(searchWord) : null;
    }

    private BooleanExpression containsKeywordInDirector(String searchWord) {
        return searchWord != null ? movie.director.containsIgnoreCase(searchWord) : null;
    }

    private BooleanExpression containsKeywordInGenre(String searchWord) {
        return searchWord != null ? movie.genre.genreName.containsIgnoreCase(searchWord) : null;
    }

}

