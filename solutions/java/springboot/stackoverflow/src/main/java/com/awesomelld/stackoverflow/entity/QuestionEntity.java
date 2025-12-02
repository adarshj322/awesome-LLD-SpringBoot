package com.awesomelld.stackoverflow.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "questions")
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 180)
    private String title;

    @Column(nullable = false, length = 4000)
    private String body;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id")
    private UserEntity author;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "question_tag",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<TagEntity> tags = new HashSet<>();

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("createdAt asc")
    private List<AnswerEntity> answers = new ArrayList<>();

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("createdAt asc")
    private List<CommentEntity> comments = new ArrayList<>();

    public QuestionEntity(UserEntity author, String title, String body) {
        this.author = author;
        this.title = title;
        this.body = body;
        this.createdAt = Instant.now();
    }
}
