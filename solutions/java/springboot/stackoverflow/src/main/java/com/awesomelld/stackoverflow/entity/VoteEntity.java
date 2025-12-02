package com.awesomelld.stackoverflow.entity;

import com.awesomelld.stackoverflow.enums.PostType;
import com.awesomelld.stackoverflow.enums.VoteType;
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
@Table(name = "votes")
public class VoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private VoteType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private PostType targetType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "voter_id")
    private UserEntity voter;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuestionEntity question;

    @ManyToOne
    @JoinColumn(name = "answer_id")
    private AnswerEntity answer;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    public VoteEntity(UserEntity voter, VoteType type, PostType targetType) {
        this.voter = voter;
        this.type = type;
        this.targetType = targetType;
        this.createdAt = Instant.now();
    }
}
