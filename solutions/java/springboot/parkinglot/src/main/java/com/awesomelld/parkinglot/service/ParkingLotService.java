package com.awesomelld.parkinglot.service;

import com.awesomelld.parkinglot.dto.ParkVehicleRequest;
import com.awesomelld.parkinglot.dto.ParkVehicleResponse;
import com.awesomelld.parkinglot.dto.ParkingLotSummaryResponse;
import com.awesomelld.parkinglot.dto.ParkingSpotResponse;
import com.awesomelld.parkinglot.dto.ReleaseVehicleResponse;
import com.awesomelld.parkinglot.dto.TicketResponse;
import com.awesomelld.parkinglot.entity.ParkingLotEntity;
import com.awesomelld.parkinglot.entity.ParkingSpotEntity;
import com.awesomelld.parkinglot.entity.ParkingTicketEntity;
import com.awesomelld.parkinglot.enums.SpotStatus;
import com.awesomelld.parkinglot.enums.TicketStatus;
import com.awesomelld.parkinglot.exception.NoAvailableSpotException;
import com.awesomelld.parkinglot.exception.ResourceNotFoundException;
import com.awesomelld.parkinglot.exception.TicketStateException;
import com.awesomelld.parkinglot.mapper.ParkingLotMapper;
import com.awesomelld.parkinglot.repository.ParkingLotRepository;
import com.awesomelld.parkinglot.repository.ParkingSpotRepository;
import com.awesomelld.parkinglot.repository.ParkingTicketRepository;
import com.awesomelld.parkinglot.support.FeeCalculator;
import com.awesomelld.parkinglot.support.VehicleSizePolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParkingLotService {

    private final ParkingLotRepository lotRepository;
    private final ParkingSpotRepository spotRepository;
    private final ParkingTicketRepository ticketRepository;
    private final ParkingLotMapper mapper;
    private final FeeCalculator feeCalculator;
    private final VehicleSizePolicy vehicleSizePolicy;

    public List<ParkingLotSummaryResponse> listLots() {
        return mapper.toLotSummaries(lotRepository.findAll());
    }

    public ParkingLotSummaryResponse getLot(String code) {
        ParkingLotEntity lot = getLotEntity(code);
        return mapper.toLotSummary(lot);
    }

    public List<ParkingSpotResponse> listSpots(String lotCode) {
        return mapper.toSpotResponses(spotRepository.findByFloor_Lot_CodeOrderByFloor_FloorNumberAscIdAsc(lotCode));
    }

    @Transactional
    public ParkVehicleResponse parkVehicle(String lotCode, ParkVehicleRequest request) {
        ParkingLotEntity lot = getLotEntity(lotCode);

        ticketRepository.findByVehicleNumberAndStatusAndSpot_Floor_Lot_Code(request.vehicleNumber(),
                TicketStatus.OPEN, lotCode).ifPresent(ticket -> {
            throw new TicketStateException("Vehicle is already parked with ticket " + ticket.getId());
        });

        List<ParkingSpotEntity> availableSpots = spotRepository.findAvailableSpots(lot.getCode(),
                SpotStatus.AVAILABLE,
                vehicleSizePolicy.allowedSizes(request.vehicleType()));

        ParkingSpotEntity spot = availableSpots.stream()
                .findFirst()
                .orElseThrow(() -> new NoAvailableSpotException("No available spot for " + request.vehicleType()));

        spot.setStatus(SpotStatus.OCCUPIED);
        spotRepository.save(spot);

        ParkingTicketEntity ticket = new ParkingTicketEntity(request.vehicleNumber(), request.vehicleType(), spot);
        ParkingTicketEntity savedTicket = ticketRepository.save(ticket);

        return new ParkVehicleResponse(savedTicket.getId(), spot.getLabel(), spot.getVehicleSize(), savedTicket.getEntryTime());
    }

    @Transactional
    public ReleaseVehicleResponse releaseVehicle(String lotCode, UUID ticketId) {
        ParkingTicketEntity ticket = ticketRepository.findByIdAndSpot_Floor_Lot_Code(ticketId, lotCode)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket %s not found in lot %s".formatted(ticketId, lotCode)));

        if (ticket.getStatus() == TicketStatus.CLOSED) {
            throw new TicketStateException("Ticket " + ticket.getId() + " is already closed");
        }

        Instant exitTime = Instant.now();
        Duration duration = Duration.between(ticket.getEntryTime(), exitTime);
        ticket.setExitTime(exitTime);
        ticket.setFee(feeCalculator.calculate(ticket.getVehicleType(), duration));
        ticket.setStatus(TicketStatus.CLOSED);

        ParkingSpotEntity spot = ticket.getSpot();
        spot.setStatus(SpotStatus.AVAILABLE);

        ticketRepository.save(ticket);
        spotRepository.save(spot);

        return new ReleaseVehicleResponse(ticket.getId(), ticket.getFee(), exitTime);
    }

    public TicketResponse getTicket(String lotCode, UUID ticketId) {
        ParkingTicketEntity ticket = ticketRepository.findByIdAndSpot_Floor_Lot_Code(ticketId, lotCode)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket %s not found".formatted(ticketId)));
        return mapper.toTicketResponse(ticket);
    }

    private ParkingLotEntity getLotEntity(String code) {
        return lotRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Parking lot not found with code " + code));
    }
}
