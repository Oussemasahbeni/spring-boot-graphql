package com.graphql.demo.service;


import com.graphql.demo.dto.AuthorRequest;
import com.graphql.demo.entity.Author;
import com.graphql.demo.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public Author createAuthor(AuthorRequest request) {
        Author author = Author.builder()
                .name(request.name())
                .email(request.email())
                .bio(request.bio())
                .build();
        return authorRepository.save(author);
    }

    public List<Author> createBatch(List<AuthorRequest> requests) {

        List<Author> authors = requests.stream().map(request -> Author.builder()
                .name(request.name())
                .email(request.email())
                .bio(request.bio())
                .build()).toList();

        return authorRepository.saveAll(authors);
    }
}
