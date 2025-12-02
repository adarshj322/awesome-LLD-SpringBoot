package com.awesomelld.parkinglot.repository;

import com.awesomelld.parkinglot.entity.ParkingTicketEntity;
import com.awesomelld.parkinglot.enums.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ParkingTicketRepository extends JpaRepository<ParkingTicketEntity, UUID> {
    Optional<ParkingTicketEntity> findByIdAndSpot_Floor_Lot_Code(UUID id, String lotCode);

    Optional<ParkingTicketEntity> findByVehicleNumberAndStatusAndSpot_Floor_Lot_Code(String vehicleNumber,
                                                                                     TicketStatus status,
                                                                                     String lotCode);
}
