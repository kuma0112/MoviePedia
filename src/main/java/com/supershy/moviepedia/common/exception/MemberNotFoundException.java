package com.supershy.moviepedia.common.exception;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(String message) {
            super(message);
        }
}
