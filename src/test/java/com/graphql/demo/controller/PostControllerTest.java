package com.graphql.demo.controller;

import com.graphql.demo.entity.Author;
import com.graphql.demo.entity.Post;
import com.graphql.demo.entity.PostType;
import com.graphql.demo.repository.AuthorRepository;
import com.graphql.demo.repository.PostRepository;
import com.graphql.demo.service.PostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@Import({PostController.class})
@GraphQlTest(PostControllerTest.class)
public class PostControllerTest {

    @Autowired
    GraphQlTester graphQlTester;

    @MockitoBean
    PostRepository postRepository;

    @MockitoBean
    AuthorRepository authorRepository;

    @MockitoBean
    PostService postService;


    @Test
    void testFindAllPostsShouldReturnAllPosts() {
        List<Post> mockPosts = new ArrayList<>();
        for (int i = 1; i <= 21; i++) {
            Post post = new Post();
            post.setId(UUID.randomUUID());
            post.setTitle("Post " + i);
            post.setCategory("Category " + i);
            post.setType(PostType.NEWS);
            post.setContent("Content " + i);
            mockPosts.add(post);
        }
        when(postService.getAllPosts()).thenReturn(mockPosts);


        // language=GraphQL
        String document = """
                 query {
                  getPosts {
                         id,
                         title,
                         category,
                         type,
                         content
                        }
                 }
                """;

        graphQlTester.document(document)
                .execute()
                .path("getPosts")
                .entityList(Post.class)
                .hasSize(21);
    }

    @Test
    void testGetPostByIdShouldReturnPost() {
        // Arrange
        UUID postId = UUID.randomUUID();
        Author mockAuthor = createMockAuthor();
        Post mockPost = createMockPost(mockAuthor);
        mockPost.setId(postId);
        when(postService.getPostById(postId)).thenReturn(mockPost);

        // language=GraphQL
        String document = """
                    query GetPostById($id: ID!) {
                      getPostById(id: $id) {
                        id
                        title
                        content
                        author {
                            id
                            name
                         }
                      }
                    }
                """;

        graphQlTester.document(document)
                .variable("id", postId.toString())
                .execute()
                .path("getPostById")
                .entity(Post.class)
                .satisfies(post -> {
                    Assertions.assertEquals(postId, post.getId());
                    Assertions.assertEquals("Specific Post", post.getTitle());
                    Assertions.assertEquals("Author 1", post.getAuthor().getName());
                });
    }

    private Post createMockPost(Author author) {
        Post post = new Post();
        post.setId(UUID.randomUUID()); // Use UUID for ID
        post.setTitle("Specific Post");
        post.setContent("Some Content");
        post.setCategory("Some Category");
        post.setType(PostType.ARTICLE); // Default PostType
        post.setAuthor(author);
        return post;
    }

    private Author createMockAuthor() {
        Author author = new Author();
        author.setId(UUID.randomUUID());
        author.setName("Author 1");
        author.setEmail("Author 1".toLowerCase().replace(" ", ".") + "@example.com");
        author.setBio("A sample bio for " + "Author 1");
        return author;
    }
}