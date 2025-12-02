package com.awesomelld.vendingmachine.entity;

import com.awesomelld.vendingmachine.enums.PaymentMethod;
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

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "payment_line")
public class PaymentLineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private PurchaseSessionEntity session;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private PaymentMethod method;

    @Column(length = 16)
    private String denomination;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private Instant capturedAt = Instant.now();

    public PaymentLineEntity(PaymentMethod method, BigDecimal amount, String denomination) {
        this.method = method;
        this.amount = amount;
        this.denomination = denomination;
        this.capturedAt = Instant.now();
    }
}
