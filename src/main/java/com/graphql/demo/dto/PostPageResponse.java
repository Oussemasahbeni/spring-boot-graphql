package com.graphql.demo.dto;

import java.util.List;

public record PostPageResponse<T>(
        List<T> content,
        int totalPages,
        long totalElements,
        int pageNumber,
        int pageSize
) {
}