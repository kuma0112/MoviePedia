package com.supershy.moviepedia.like.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLike is a Querydsl query type for Like
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLike extends EntityPathBase<Like> {

    private static final long serialVersionUID = 600129066L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLike like = new QLike("like1");

    public final com.supershy.moviepedia.like.entity.id.QLikeId likeId;

    public final com.supershy.moviepedia.member.entity.QMember member;

    public final com.supershy.moviepedia.movie.entity.QMovie movie;

    public QLike(String variable) {
        this(Like.class, forVariable(variable), INITS);
    }

    public QLike(Path<? extends Like> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLike(PathMetadata metadata, PathInits inits) {
        this(Like.class, metadata, inits);
    }

    public QLike(Class<? extends Like> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.likeId = inits.isInitialized("likeId") ? new com.supershy.moviepedia.like.entity.id.QLikeId(forProperty("likeId")) : null;
        this.member = inits.isInitialized("member") ? new com.supershy.moviepedia.member.entity.QMember(forProperty("member")) : null;
        this.movie = inits.isInitialized("movie") ? new com.supershy.moviepedia.movie.entity.QMovie(forProperty("movie"), inits.get("movie")) : null;
    }

}

