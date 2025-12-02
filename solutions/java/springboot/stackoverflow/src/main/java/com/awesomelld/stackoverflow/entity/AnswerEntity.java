package com.awesomelld.stackoverflow.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "answers")
public class AnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 4000)
    private String body;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id")
    private UserEntity author;

    @ManyToOne(optional = false)
    @JoinColumn(name = "question_id")
    private QuestionEntity question;

    @Column(nullable = false)
    private boolean accepted = false;

    @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("createdAt asc")
    private List<CommentEntity> comments = new ArrayList<>();

    public AnswerEntity(UserEntity author, QuestionEntity question, String body) {
        this.author = author;
        this.question = question;
        this.body = body;
        this.createdAt = Instant.now();
    }
}
