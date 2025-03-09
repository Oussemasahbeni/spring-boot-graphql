package com.graphql.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String email;

    private String bio;
    
    @OneToMany(mappedBy = "author")
    private List<Post> posts;
}
