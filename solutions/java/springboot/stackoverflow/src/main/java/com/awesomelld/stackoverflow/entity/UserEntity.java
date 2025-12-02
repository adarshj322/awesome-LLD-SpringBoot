package com.awesomelld.stackoverflow.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 64)
    private String displayName;

    @Column(nullable = false)
    private int reputation = 0;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    public UserEntity(String displayName, int reputation) {
        this.displayName = displayName;
        this.reputation = reputation;
        this.createdAt = Instant.now();
    }
}
