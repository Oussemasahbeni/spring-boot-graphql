package com.graphql.demo.controller;

import com.graphql.demo.dto.PageResponse;
import com.graphql.demo.dto.PostRequest;
import com.graphql.demo.entity.Post;
import com.graphql.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @SchemaMapping(typeName = "Query", value = "getPosts")
    public List<Post> getPosts() {
        return postService.getAllPosts();
    }

    @QueryMapping
    public List<Post> getCurrentUserPosts(Principal principal) {
        return postService.getCurrentUserPosts(UUID.fromString(principal.getName()));
    }

    @QueryMapping
    public Post getPostById(@Argument UUID id) {
        return postService.getPostById(id);
    }

    @QueryMapping
    public PageResponse<Post> getPostsPage(
            @Argument Optional<Integer> page,
            @Argument Optional<Integer> size,
            @Argument Optional<String> sort,
            @Argument Optional<String> sortDirection,
            @Argument Optional<String> criteria
    ) {
        int pageNumber = page.orElse(0);
        int pageSize = size.orElse(10);
        String sortBy = sort.orElse("id");
        String direction = sortDirection.orElse("DESC");
        String searchCriteria = criteria.orElse("");

        return postService.getAllPosts(pageNumber, pageSize, sortBy, direction, searchCriteria);
    }

    @MutationMapping
    public Post createPost(@Argument PostRequest post) {
        return postService.createPost(post);
    }

    @MutationMapping
    public List<Post> createBatchPosts(@Argument List<PostRequest> posts) {
        return postService.createBatch(posts);
    }

    @MutationMapping
    public Post updatePost(@Argument UUID id, @Argument PostRequest post) {

        return postService.updatePost(id, post);
    }

    @MutationMapping
    public Boolean deletePost(@Argument UUID id) {
        return postService.deleteById(id);

    }

}
