package com.awesomelld.vendingmachine.entity;

import com.awesomelld.vendingmachine.enums.PurchaseState;
import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "purchase_session")
public class PurchaseSessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id", nullable = false)
    private VendingMachineEntity machine;

    @Column(nullable = false)
    private Instant startedAt = Instant.now();

    private Instant completedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private PurchaseState state = PurchaseState.SELECTING;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal totalCost = BigDecimal.ZERO;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal amountPaid = BigDecimal.ZERO;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal changeDue = BigDecimal.ZERO;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseItemEntity> items = new ArrayList<>();

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentLineEntity> payments = new ArrayList<>();

    public void addItem(PurchaseItemEntity item) {
        items.add(item);
        item.setSession(this);
    }

    public void addPayment(PaymentLineEntity payment) {
        payments.add(payment);
        payment.setSession(this);
    }
}
