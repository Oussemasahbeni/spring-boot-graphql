package com.graphql.demo.controller;

import com.graphql.demo.dto.AuthorRequest;
import com.graphql.demo.entity.Author;
import com.graphql.demo.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @QueryMapping
    public List<Author> getAuthors() {
        return authorService.getAllAuthors();
    }

    @QueryMapping
    public Author getAuthorById(@Argument UUID id) {
        return authorService.getAuthorById(id);
    }


    @MutationMapping
    public Author createAuthor(@Argument AuthorRequest author) {
        return authorService.createAuthor(author);
    }

    @MutationMapping
    public List<Author> createBatchAuthors(@Argument List<AuthorRequest> authors) {
        return authorService.createBatch(authors);
    }

    @MutationMapping
    public Author updateAuthor(@Argument UUID id, @Argument AuthorRequest author) {
        return authorService.updateAuthor(id, author);
    }

    //    @Secured("ROLE_ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    @MutationMapping
    public Boolean deleteAuthor(@Argument UUID id) {
        return authorService.deleteById(id);
    }
}