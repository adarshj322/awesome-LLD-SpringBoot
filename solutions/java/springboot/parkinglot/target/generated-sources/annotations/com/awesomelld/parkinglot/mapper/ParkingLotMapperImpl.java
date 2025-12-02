package com.awesomelld.parkinglot.mapper;

import com.awesomelld.parkinglot.dto.ParkingFloorResponse;
import com.awesomelld.parkinglot.dto.ParkingLotSummaryResponse;
import com.awesomelld.parkinglot.dto.ParkingSpotResponse;
import com.awesomelld.parkinglot.dto.TicketResponse;
import com.awesomelld.parkinglot.entity.ParkingFloorEntity;
import com.awesomelld.parkinglot.entity.ParkingLotEntity;
import com.awesomelld.parkinglot.entity.ParkingSpotEntity;
import com.awesomelld.parkinglot.entity.ParkingTicketEntity;
import com.awesomelld.parkinglot.enums.SpotStatus;
import com.awesomelld.parkinglot.enums.TicketStatus;
import com.awesomelld.parkinglot.enums.VehicleSize;
import com.awesomelld.parkinglot.enums.VehicleType;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-02T18:35:59+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.7 (Azul Systems, Inc.)"
)
@Component
public class ParkingLotMapperImpl implements ParkingLotMapper {

    @Override
    public TicketResponse toTicketResponse(ParkingTicketEntity entity) {
        if ( entity == null ) {
            return null;
        }

        ParkingSpotResponse spot = null;
        UUID ticketId = null;
        String vehicleNumber = null;
        VehicleType vehicleType = null;
        Instant entryTime = null;
        Instant exitTime = null;
        TicketStatus status = null;
        BigDecimal fee = null;

        spot = toSpotResponse( entity.getSpot() );
        ticketId = entity.getId();
        vehicleNumber = entity.getVehicleNumber();
        vehicleType = entity.getVehicleType();
        entryTime = entity.getEntryTime();
        exitTime = entity.getExitTime();
        status = entity.getStatus();
        fee = entity.getFee();

        TicketResponse ticketResponse = new TicketResponse( ticketId, vehicleNumber, vehicleType, entryTime, exitTime, status, fee, spot );

        return ticketResponse;
    }

    @Override
    public ParkingFloorResponse toFloorResponse(ParkingFloorEntity entity) {
        if ( entity == null ) {
            return null;
        }

        int number = 0;
        Long id = null;
        List<ParkingSpotResponse> spots = null;

        number = entity.getFloorNumber();
        id = entity.getId();
        spots = toSpotResponses( entity.getSpots() );

        ParkingFloorResponse parkingFloorResponse = new ParkingFloorResponse( id, number, spots );

        return parkingFloorResponse;
    }

    @Override
    public ParkingSpotResponse toSpotResponse(ParkingSpotEntity entity) {
        if ( entity == null ) {
            return null;
        }

        int floorNumber = 0;
        Long id = null;
        String label = null;
        VehicleSize vehicleSize = null;
        SpotStatus status = null;

        floorNumber = entityFloorFloorNumber( entity );
        id = entity.getId();
        label = entity.getLabel();
        vehicleSize = entity.getVehicleSize();
        status = entity.getStatus();

        ParkingSpotResponse parkingSpotResponse = new ParkingSpotResponse( id, label, vehicleSize, status, floorNumber );

        return parkingSpotResponse;
    }

    @Override
    public List<ParkingSpotResponse> toSpotResponses(List<ParkingSpotEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<ParkingSpotResponse> list = new ArrayList<ParkingSpotResponse>( entities.size() );
        for ( ParkingSpotEntity parkingSpotEntity : entities ) {
            list.add( toSpotResponse( parkingSpotEntity ) );
        }

        return list;
    }

    @Override
    public ParkingLotSummaryResponse toLotSummary(ParkingLotEntity entity) {
        if ( entity == null ) {
            return null;
        }

        List<ParkingFloorResponse> floors = null;
        Long id = null;
        String code = null;
        String displayName = null;

        floors = parkingFloorEntityListToParkingFloorResponseList( entity.getFloors() );
        id = entity.getId();
        code = entity.getCode();
        displayName = entity.getDisplayName();

        ParkingLotSummaryResponse parkingLotSummaryResponse = new ParkingLotSummaryResponse( id, code, displayName, floors );

        return parkingLotSummaryResponse;
    }

    @Override
    public List<ParkingLotSummaryResponse> toLotSummaries(List<ParkingLotEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<ParkingLotSummaryResponse> list = new ArrayList<ParkingLotSummaryResponse>( entities.size() );
        for ( ParkingLotEntity parkingLotEntity : entities ) {
            list.add( toLotSummary( parkingLotEntity ) );
        }

        return list;
    }

    private int entityFloorFloorNumber(ParkingSpotEntity parkingSpotEntity) {
        if ( parkingSpotEntity == null ) {
            return 0;
        }
        ParkingFloorEntity floor = parkingSpotEntity.getFloor();
        if ( floor == null ) {
            return 0;
        }
        int floorNumber = floor.getFloorNumber();
        return floorNumber;
    }

    protected List<ParkingFloorResponse> parkingFloorEntityListToParkingFloorResponseList(List<ParkingFloorEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<ParkingFloorResponse> list1 = new ArrayList<ParkingFloorResponse>( list.size() );
        for ( ParkingFloorEntity parkingFloorEntity : list ) {
            list1.add( toFloorResponse( parkingFloorEntity ) );
        }

        return list1;
    }
}
