package com.awesomelld.rideshare.entity;

import com.awesomelld.rideshare.enums.TripStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "trip")
public class TripEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rider_id", nullable = false)
    private RiderEntity rider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private DriverEntity driver;

    private Double pickupLat;
    private Double pickupLng;
    private Double dropLat;
    private Double dropLng;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private TripStatus status = TripStatus.REQUESTED;

    @Column(precision = 10, scale = 2)
    private BigDecimal fareEstimate;

    @Column(precision = 10, scale = 2)
    private BigDecimal fareFinal;

    @Column(nullable = false)
    private Instant requestedAt = Instant.now();

    private Instant startedAt;
    private Instant completedAt;
}
