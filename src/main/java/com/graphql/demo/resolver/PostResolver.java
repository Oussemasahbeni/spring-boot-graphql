package com.graphql.demo.resolver;

import com.graphql.demo.dto.PostPageResponse;
import com.graphql.demo.entity.Post;
import com.graphql.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class PostResolver {

    private final PostService postService;

    @QueryMapping
    public List<Post> getPosts() {
        return postService.getAllPosts();
    }

    @QueryMapping
    public Post getPostById(@Argument UUID id) {
        return postService.getPostById(id);
    }

    @QueryMapping
    public PostPageResponse<Post> getPostsPage(
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

}
