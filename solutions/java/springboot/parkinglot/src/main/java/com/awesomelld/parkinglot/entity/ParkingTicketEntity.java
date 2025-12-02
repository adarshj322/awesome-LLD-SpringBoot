package com.awesomelld.parkinglot.entity;

import com.awesomelld.parkinglot.enums.TicketStatus;
import com.awesomelld.parkinglot.enums.VehicleType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "parking_ticket")
public class ParkingTicketEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "vehicle_number", nullable = false, length = 32)
    private String vehicleNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type", nullable = false, length = 16)
    private VehicleType vehicleType;

    @Column(name = "entry_time", nullable = false)
    private Instant entryTime;

    @Column(name = "exit_time")
    private Instant exitTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "ticket_status", nullable = false, length = 16)
    private TicketStatus status = TicketStatus.OPEN;

    @Column(precision = 10, scale = 2)
    private BigDecimal fee;

    @ManyToOne(optional = false)
    @JoinColumn(name = "spot_id", nullable = false)
    private ParkingSpotEntity spot;

    public ParkingTicketEntity(String vehicleNumber, VehicleType vehicleType, ParkingSpotEntity spot) {
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.spot = spot;
        this.entryTime = Instant.now();
        this.status = TicketStatus.OPEN;
    }
}
