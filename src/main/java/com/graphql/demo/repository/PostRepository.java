package com.graphql.demo.repository;

import com.graphql.demo.entity.Post;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    Page<Post> findByTitleContainingIgnoreCase(String searchCriteria, Pageable pageable);

    List<Post> findByAuthorId(UUID id);

    Window<Post> findByAuthorId(UUID id, ScrollPosition position, Limit limit, Sort sort);
}
