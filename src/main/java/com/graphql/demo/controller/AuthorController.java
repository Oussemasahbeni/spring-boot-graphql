package com.graphql.demo.controller;

import com.graphql.demo.dto.AuthorRequest;
import com.graphql.demo.entity.Author;
import com.graphql.demo.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/author")
public class AuthorController {


    private final AuthorService authorService;

    @PostMapping("/create")
    public ResponseEntity<Author> createAuthor(@RequestBody AuthorRequest author) {
        return ResponseEntity.ok(authorService.createAuthor(author));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Author>> createBatch(@RequestBody List<AuthorRequest> authors) {
        return ResponseEntity.ok(authorService.createBatch(authors));
    }
}
