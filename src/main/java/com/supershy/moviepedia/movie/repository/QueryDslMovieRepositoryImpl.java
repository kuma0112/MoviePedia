package com.supershy.moviepedia.movie.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
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
    public List<Movie> getMoviesBySearchWord(String searchWord, int page, int size) {

        QMovie movie = QMovie.movie;

        // 가중치 계산: 제목에 검색어가 포함되면 3점, 감독에 포함되면 2점, 장르에 포함되면 1점
        NumberExpression<Integer> relevanceScore = new CaseBuilder()
                .when(movie.title.containsIgnoreCase(searchWord)).then(3)
                .otherwise(0)
                .add(
                        new CaseBuilder()
                                .when(movie.director.containsIgnoreCase(searchWord)).then(2)
                                .otherwise(0)
                )
                .add(
                        new CaseBuilder()
                                .when(movie.genre.genreName.containsIgnoreCase(searchWord)).then(1)
                                .otherwise(0)
                );


        return jpaQueryFactory
                .select(movie)
                .from(movie)
                .where(
                        containsKeywordInTitle(searchWord)
                                .or(containsKeywordInDirector(searchWord))
                                .or(containsKeywordInGenre(searchWord))
                )
                .orderBy(relevanceScore.desc())
                .offset(page * size)
                .limit(size)
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

