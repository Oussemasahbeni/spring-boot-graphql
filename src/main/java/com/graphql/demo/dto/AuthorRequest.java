package com.graphql.demo.dto;

import java.time.LocalDateTime;

public record AuthorRequest(
        String name,
        String email,
        String bio,
        LocalDateTime birthDate
) {
}
