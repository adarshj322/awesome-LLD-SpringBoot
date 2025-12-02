package com.awesomelld.atm.entity;

import com.awesomelld.atm.enums.AtmStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "atm")
public class AtmEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 16)
    private String code;

    @Column(nullable = false, length = 128)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private AtmStatus status = AtmStatus.ACTIVE;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal cashAvailable = BigDecimal.ZERO;

    public AtmEntity(String code, String location, BigDecimal cashAvailable) {
        this.code = code;
        this.location = location;
        this.status = AtmStatus.ACTIVE;
        this.cashAvailable = cashAvailable;
    }
}
