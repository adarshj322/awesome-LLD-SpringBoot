package com.awesomelld.parkinglot.repository;

import com.awesomelld.parkinglot.entity.ParkingSpotEntity;
import com.awesomelld.parkinglot.enums.SpotStatus;
import com.awesomelld.parkinglot.enums.VehicleSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpotEntity, Long> {

    @Query("""
            select s from ParkingSpotEntity s
            where s.floor.lot.code = :lotCode
              and s.status = :status
              and s.vehicleSize in :allowedSizes
            order by s.floor.floorNumber asc, s.id asc
            """)
    List<ParkingSpotEntity> findAvailableSpots(@Param("lotCode") String lotCode,
                                               @Param("status") SpotStatus status,
                                               @Param("allowedSizes") Collection<VehicleSize> allowedSizes);

    List<ParkingSpotEntity> findByFloor_Lot_CodeOrderByFloor_FloorNumberAscIdAsc(String lotCode);
}
