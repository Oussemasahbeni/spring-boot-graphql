package com.graphql.demo.controller;

import com.graphql.demo.dto.PostRequest;
import com.graphql.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @PostMapping("/create")
    public ResponseEntity<Void> createPost(@RequestBody PostRequest post) {
        postService.createPost(post);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/batch")
    public ResponseEntity<Void> createBatch(@RequestBody List<PostRequest> posts) {
        postService.createBatch(posts);
        return ResponseEntity.ok().build();
    }
}
