package com.supershy.moviepedia.movie.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMovie is a Querydsl query type for Movie
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMovie extends EntityPathBase<Movie> {

    private static final long serialVersionUID = -232961374L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMovie movie = new QMovie("movie");

    public final StringPath description = createString("description");

    public final StringPath director = createString("director");

    public final com.supershy.moviepedia.genre.entity.QGenre genre;

    public final StringPath imageUrl = createString("imageUrl");

    public final NumberPath<Long> movieId = createNumber("movieId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> releaseDate = createDateTime("releaseDate", java.time.LocalDateTime.class);

    public final EnumPath<ReleaseState> releaseState = createEnum("releaseState", ReleaseState.class);

    public final NumberPath<Double> reservationRate = createNumber("reservationRate", Double.class);

    public final ListPath<com.supershy.moviepedia.review.entity.Review, com.supershy.moviepedia.review.entity.QReview> reviews = this.<com.supershy.moviepedia.review.entity.Review, com.supershy.moviepedia.review.entity.QReview>createList("reviews", com.supershy.moviepedia.review.entity.Review.class, com.supershy.moviepedia.review.entity.QReview.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public QMovie(String variable) {
        this(Movie.class, forVariable(variable), INITS);
    }

    public QMovie(Path<? extends Movie> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMovie(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMovie(PathMetadata metadata, PathInits inits) {
        this(Movie.class, metadata, inits);
    }

    public QMovie(Class<? extends Movie> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.genre = inits.isInitialized("genre") ? new com.supershy.moviepedia.genre.entity.QGenre(forProperty("genre")) : null;
    }

}

