package com.graphql.demo.service;

import com.graphql.demo.dto.PostPageResponse;
import com.graphql.demo.dto.PostRequest;
import com.graphql.demo.entity.Author;
import com.graphql.demo.entity.Post;
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


    public void createPost(PostRequest request) {

        Author author = authorRepository.findById(request.authorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        Post post = Post.builder()
                .title(request.title())
                .category(request.category())
                .content(request.content())
                .author(author)
                .build();
        postRepository.save(post);
    }


    public void createBatch(List<PostRequest> requests) {

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
        postRepository.saveAll(posts);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(UUID id) {
        System.out.println("PostService.getPostById");
        return this.postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public PostPageResponse<Post> getAllPosts(int page, int size, String sortBy, String direction, String searchCriteria) {
        Sort sort = direction.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Post> postPage;

        if (searchCriteria.isBlank()) {
            postPage = postRepository.findAll(pageable);
        } else {
            postPage = postRepository.findByTitleContainingIgnoreCase(searchCriteria, pageable);
        }

        return new PostPageResponse<Post>(
                postPage.getContent(),
                postPage.getTotalPages(),
                postPage.getTotalElements(),
                postPage.getNumber(),
                postPage.getSize()
        );
    }
}
