package com.graphql.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue
    private UUID id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "category", nullable = false)
    private String category;
    @Column(name = "content", nullable = false)
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;


}
