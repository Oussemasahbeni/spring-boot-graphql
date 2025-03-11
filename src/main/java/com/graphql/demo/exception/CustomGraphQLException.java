package com.graphql.demo.exception;

import lombok.Getter;

@Getter
public class CustomGraphQLException extends RuntimeException {
    private final String errorCode;

    public CustomGraphQLException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}