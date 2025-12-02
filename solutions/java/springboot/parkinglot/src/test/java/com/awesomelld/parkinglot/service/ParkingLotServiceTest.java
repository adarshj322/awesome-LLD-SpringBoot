package com.awesomelld.parkinglot.service;

import com.awesomelld.parkinglot.dto.ParkVehicleRequest;
import com.awesomelld.parkinglot.dto.ParkVehicleResponse;
import com.awesomelld.parkinglot.dto.ReleaseVehicleResponse;
import com.awesomelld.parkinglot.dto.TicketResponse;
import com.awesomelld.parkinglot.enums.SpotStatus;
import com.awesomelld.parkinglot.enums.TicketStatus;
import com.awesomelld.parkinglot.enums.VehicleType;
import com.awesomelld.parkinglot.repository.ParkingSpotRepository;
import com.awesomelld.parkinglot.repository.ParkingTicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ParkingLotServiceTest {

    private static final String LOT_CODE = "CITY_CENTER";

    @Autowired
    private ParkingLotService service;

    @Autowired
    private ParkingSpotRepository spotRepository;

    @Autowired
    private ParkingTicketRepository ticketRepository;

    @BeforeEach
    void setUp() {
        ticketRepository.deleteAll();
        spotRepository.findByFloor_Lot_CodeOrderByFloor_FloorNumberAscIdAsc(LOT_CODE)
                .forEach(spot -> {
                    spot.setStatus(SpotStatus.AVAILABLE);
                    spotRepository.save(spot);
                });
    }

    @Test
    void parkVehicle_shouldAllocateFirstAvailableSpot() {
        ParkVehicleResponse response = service.parkVehicle(LOT_CODE, new ParkVehicleRequest("TEST-1", VehicleType.CAR));

        assertThat(response.ticketId()).isNotNull();
        assertThat(service.listSpots(LOT_CODE))
                .anyMatch(spot -> spot.label().equals(response.spotLabel()) && spot.status() == SpotStatus.OCCUPIED);
    }

    @Test
    void releaseVehicle_shouldCloseTicketAndFreeSpot() {
        ParkVehicleResponse response = service.parkVehicle(LOT_CODE, new ParkVehicleRequest("TEST-2", VehicleType.BIKE));

        ReleaseVehicleResponse releaseResponse = service.releaseVehicle(LOT_CODE, response.ticketId());
        assertThat(releaseResponse.ticketId()).isEqualTo(response.ticketId());
        assertThat(releaseResponse.fee()).isNotNull();

        TicketResponse ticket = service.getTicket(LOT_CODE, response.ticketId());
        assertThat(ticket.status()).isEqualTo(TicketStatus.CLOSED);
        assertThat(service.listSpots(LOT_CODE))
                .anyMatch(spot -> spot.label().equals(response.spotLabel()) && spot.status() == SpotStatus.AVAILABLE);
    }
}
