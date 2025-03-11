package com.graphql.demo.service;

import com.graphql.demo.dto.PageResponse;
import com.graphql.demo.dto.PostRequest;
import com.graphql.demo.entity.Author;
import com.graphql.demo.entity.Post;
import com.graphql.demo.exception.CustomGraphQLException;
import com.graphql.demo.repository.AuthorRepository;
import com.graphql.demo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final AuthorRepository authorRepository;


    public Post createPost(PostRequest request) {

        Author author = authorRepository.findById(request.authorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        Post post = Post.builder()
                .title(request.title())
                .category(request.category())
                .content(request.content())
                .type(request.type())
                .author(author)
                .build();
        return postRepository.save(post);
    }


    public List<Post> createBatch(List<PostRequest> requests) {

        Author author = authorRepository.findById(requests.getFirst().authorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        List<Post> posts = requests.stream()
                .map(request -> Post.builder()
                        .title(request.title())
                        .category(request.category())
                        .content(request.content())
                        .author(author)
                        .build())
                .toList();
        return postRepository.saveAll(posts);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(UUID id) {
        return this.postRepository.findById(id).orElseThrow(() -> new CustomGraphQLException("Post not found", "405"));
    }

    public PageResponse<Post> getAllPosts(int page, int size, String sortBy, String direction, String searchCriteria) {
        Sort sort = direction.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Post> postPage;

        if (searchCriteria.isBlank()) {
            postPage = postRepository.findAll(pageable);
        } else {
            postPage = postRepository.findByTitleContainingIgnoreCase(searchCriteria, pageable);
        }

        return new PageResponse<>(
                postPage.getContent(),
                postPage.getTotalPages(),
                postPage.getTotalElements(),
                postPage.getNumber(),
                postPage.getSize()
        );
    }

    public Post updatePost(UUID id, PostRequest post) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new CustomGraphQLException("Post not found", "405"));

        Author author = authorRepository.findById(post.authorId())
                .orElseThrow(() -> new CustomGraphQLException("Author not found", "405"));

        existingPost.setTitle(post.title());
        existingPost.setCategory(post.category());
        existingPost.setContent(post.content());
        existingPost.setAuthor(author);

        return postRepository.save(existingPost);
    }

    public Boolean deleteById(UUID id) {
        try {
            postRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public List<Post> getCurrentUserPosts(UUID id) {
        return postRepository.findByAuthorId(id);
    }
}
