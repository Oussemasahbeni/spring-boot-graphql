package com.graphql.demo.dto;

public record AuthorRequest(
        String name,
        String email,
        String bio
) {
}
