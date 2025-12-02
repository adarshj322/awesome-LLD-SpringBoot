package com.awesomelld.parkinglot.mapper;

import com.awesomelld.parkinglot.dto.ParkingFloorResponse;
import com.awesomelld.parkinglot.dto.ParkingLotSummaryResponse;
import com.awesomelld.parkinglot.dto.ParkingSpotResponse;
import com.awesomelld.parkinglot.dto.TicketResponse;
import com.awesomelld.parkinglot.entity.ParkingFloorEntity;
import com.awesomelld.parkinglot.entity.ParkingLotEntity;
import com.awesomelld.parkinglot.entity.ParkingSpotEntity;
import com.awesomelld.parkinglot.entity.ParkingTicketEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParkingLotMapper {

    @Mapping(target = "spot", source = "spot")
    @Mapping(target = "ticketId", source = "id")
    TicketResponse toTicketResponse(ParkingTicketEntity entity);

    @Mapping(target = "number", source = "floorNumber")
    ParkingFloorResponse toFloorResponse(ParkingFloorEntity entity);

    @Mapping(target = "floorNumber", source = "floor.floorNumber")
    ParkingSpotResponse toSpotResponse(ParkingSpotEntity entity);

    List<ParkingSpotResponse> toSpotResponses(List<ParkingSpotEntity> entities);

    @Mapping(target = "floors", source = "floors")
    ParkingLotSummaryResponse toLotSummary(ParkingLotEntity entity);

    List<ParkingLotSummaryResponse> toLotSummaries(List<ParkingLotEntity> entities);
}
