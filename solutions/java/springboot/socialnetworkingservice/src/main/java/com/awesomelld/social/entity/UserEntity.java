package com.awesomelld.social.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "app_user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 64)
    private String username;

    @Column(nullable = false, unique = true, length = 128)
    private String email;

    @Column(nullable = false, length = 128)
    private String displayName;

    @Column(length = 256)
    private String bio;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    public UserEntity(String username, String email, String displayName, String bio) {
        this.username = username;
        this.email = email;
        this.displayName = displayName;
        this.bio = bio;
        this.createdAt = Instant.now();
    }
}
