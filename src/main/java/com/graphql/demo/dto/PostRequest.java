package com.graphql.demo.dto;

import java.util.UUID;

public record PostRequest(
        String title,
        String content,
        UUID authorId,
        String category) {
}
