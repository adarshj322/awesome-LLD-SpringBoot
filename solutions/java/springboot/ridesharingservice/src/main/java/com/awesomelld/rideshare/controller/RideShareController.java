package com.awesomelld.rideshare.controller;

import com.awesomelld.rideshare.dto.PaymentRequest;
import com.awesomelld.rideshare.dto.PaymentResponse;
import com.awesomelld.rideshare.dto.TripRequest;
import com.awesomelld.rideshare.dto.TripResponse;
import com.awesomelld.rideshare.service.RideShareService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/rides")
@RequiredArgsConstructor
public class RideShareController {

    private final RideShareService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TripResponse requestTrip(@Valid @RequestBody TripRequest request) {
        return service.requestTrip(request);
    }

    @GetMapping("/{tripId}")
    public TripResponse getTrip(@PathVariable Long tripId) {
        return service.getTripResponse(tripId);
    }

    @PostMapping("/{tripId}/start")
    public TripResponse startTrip(@PathVariable Long tripId) {
        return service.startTrip(tripId);
    }

    @PostMapping("/{tripId}/complete")
    public TripResponse completeTrip(@PathVariable Long tripId, @RequestBody BigDecimal fare) {
        return service.completeTrip(tripId, fare);
    }

    @PostMapping("/payments")
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponse pay(@Valid @RequestBody PaymentRequest request) {
        return service.pay(request);
    }
}
