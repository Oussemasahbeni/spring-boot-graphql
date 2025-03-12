package com.graphql.demo.repository;

import com.graphql.demo.entity.Post;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.graphql.data.GraphQlRepository;

import java.util.List;
import java.util.UUID;

@GraphQlRepository
public interface PostRepository extends JpaRepository<Post, UUID>, QueryByExampleExecutor<Post> {
    Page<Post> findByTitleContainingIgnoreCase(String searchCriteria, Pageable pageable);

    List<Post> findByAuthorId(UUID id);

    Window<Post> findByAuthorId(UUID id, ScrollPosition position, Limit limit, Sort sort);
}
