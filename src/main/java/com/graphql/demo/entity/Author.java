package com.graphql.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "authors")
@EntityListeners(AuditingEntityListener.class)
public class Author {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String email;

    private String bio;

    @OneToMany(mappedBy = "author")
    private List<Post> posts;
    private LocalDateTime birthDate;
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", insertable = false)
    private Instant updatedAt;

    @Version
    @Column(name = "version", nullable = false)
    private Short version;
}
