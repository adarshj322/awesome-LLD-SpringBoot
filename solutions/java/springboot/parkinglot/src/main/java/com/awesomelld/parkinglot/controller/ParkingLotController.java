package com.awesomelld.parkinglot.controller;

import com.awesomelld.parkinglot.dto.ParkVehicleRequest;
import com.awesomelld.parkinglot.dto.ParkVehicleResponse;
import com.awesomelld.parkinglot.dto.ParkingLotSummaryResponse;
import com.awesomelld.parkinglot.dto.ParkingSpotResponse;
import com.awesomelld.parkinglot.dto.ReleaseVehicleResponse;
import com.awesomelld.parkinglot.dto.TicketResponse;
import com.awesomelld.parkinglot.service.ParkingLotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/parking-lots")
@Validated
@RequiredArgsConstructor
public class ParkingLotController {

    private final ParkingLotService service;

    /**
     * Return a snapshot of all parking lots including floors and spots so client dashboards
     * can build a global occupancy view with a single call.
     */
    @GetMapping
    public List<ParkingLotSummaryResponse> listLots() {
        return service.listLots();
    }

    /**
     * Fetch detailed information for a specific lot, useful when the UI focuses on one facility
     * and needs floor/spot hierarchy and identifiers.
     */
    @GetMapping("/{code}")
    public ParkingLotSummaryResponse getLot(@PathVariable String code) {
        return service.getLot(code);
    }

    /**
     * List every spot in a lot ordered by floor so clients can display availability grids
     * or highlight which spot is assigned to a ticket.
     */
    @GetMapping("/{code}/spots")
    public List<ParkingSpotResponse> listSpots(@PathVariable String code) {
        return service.listSpots(code);
    }

    /**
     * Allocate the nearest compatible spot for the provided vehicle, create a ticket,
     * and respond with the ticket id/assigned spot for later checkout.
     */
    @PostMapping("/{code}/tickets")
    @ResponseStatus(HttpStatus.CREATED)
    public ParkVehicleResponse parkVehicle(@PathVariable String code, @Valid @RequestBody ParkVehicleRequest request) {
        return service.parkVehicle(code, request);
    }

    /**
     * Retrieve the latest ticket state (entry time, status, spot assignment, fee when available).
     */
    @GetMapping("/{code}/tickets/{ticketId}")
    public TicketResponse getTicket(@PathVariable String code, @PathVariable UUID ticketId) {
        return service.getTicket(code, ticketId);
    }

    /**
     * Mark the ticket as exited, free up the spot, and return the calculated parking fee summary.
     * This endpoint completes a parking session.
     */
    @PatchMapping("/{code}/tickets/{ticketId}/checkout")
    public ReleaseVehicleResponse releaseVehicle(@PathVariable String code, @PathVariable UUID ticketId) {
        return service.releaseVehicle(code, ticketId);
    }
}
