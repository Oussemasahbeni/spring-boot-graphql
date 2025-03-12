package com.graphql.demo.service;


import com.graphql.demo.dto.AuthorRequest;
import com.graphql.demo.entity.Author;
import com.graphql.demo.entity.Post;
import com.graphql.demo.repository.AuthorRepository;
import com.graphql.demo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Window;
import org.springframework.graphql.data.query.ScrollSubrange;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final PostRepository postRepository;

    public Author createAuthor(AuthorRequest request) {
        Author author = Author.builder()
                .name(request.name())
                .email(request.email())
                .bio(request.bio())
                .birthDate(request.birthDate())
                .build();
        return authorRepository.save(author);
    }

    public List<Author> createBatch(List<AuthorRequest> requests) {

        List<Author> authors = requests.stream().map(request -> Author.builder()
                .name(request.name())
                .email(request.email())
                .bio(request.bio())
                .birthDate(request.birthDate())
                .build()).toList();

        return authorRepository.saveAll(authors);
    }

    public Window<Post> posts(Author author, ScrollSubrange subrange) {
        ScrollPosition position = subrange.position().orElse(ScrollPosition.offset());
        Limit limit = Limit.of(subrange.count().orElse(10));
        Sort sort = Sort.by("title").ascending();
        return postRepository.findByAuthorId(author.getId(), position, limit, sort);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(UUID id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
    }

    public Author updateAuthor(UUID id, AuthorRequest author) {
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));

        existingAuthor.setName(author.name());
        existingAuthor.setEmail(author.email());
        existingAuthor.setBio(author.bio());

        return authorRepository.save(existingAuthor);
    }

    public Boolean deleteById(UUID id) {
        try {
            authorRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
