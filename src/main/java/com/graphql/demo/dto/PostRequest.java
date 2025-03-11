package com.graphql.demo.dto;

import com.graphql.demo.entity.PostType;

import java.util.UUID;

public record PostRequest(
        String title,
        String content,
        UUID authorId,
        PostType type,
        String category) {
}
