package com.graphql.demo.repository;

import com.graphql.demo.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    Page<Post> findByTitleContainingIgnoreCase(String searchCriteria, Pageable pageable);
}
