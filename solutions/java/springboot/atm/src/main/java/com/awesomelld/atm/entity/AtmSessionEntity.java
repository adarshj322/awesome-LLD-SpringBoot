package com.awesomelld.atm.entity;

import com.awesomelld.atm.enums.SessionState;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "atm_session")
public class AtmSessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", columnDefinition = "BINARY(16)", unique = true, nullable = false)
    private UUID token = UUID.randomUUID();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atm_id", nullable = false)
    private AtmEntity atm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private CardEntity card;

    @Column(nullable = false)
    private Instant startedAt = Instant.now();

    private Instant endedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private SessionState state = SessionState.ACTIVE;
}
