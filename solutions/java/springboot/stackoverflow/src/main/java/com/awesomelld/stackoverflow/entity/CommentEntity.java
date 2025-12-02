package com.awesomelld.stackoverflow.entity;

import com.awesomelld.stackoverflow.enums.PostType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 800)
    private String body;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id")
    private UserEntity author;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuestionEntity question;

    @ManyToOne
    @JoinColumn(name = "answer_id")
    private AnswerEntity answer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private PostType targetType;

    public CommentEntity(UserEntity author, String body, PostType targetType) {
        this.author = author;
        this.body = body;
        this.targetType = targetType;
        this.createdAt = Instant.now();
    }
}
