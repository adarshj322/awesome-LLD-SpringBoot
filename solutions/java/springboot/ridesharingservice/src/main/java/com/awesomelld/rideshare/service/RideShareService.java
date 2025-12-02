package com.awesomelld.rideshare.service;

import com.awesomelld.rideshare.dto.PaymentRequest;
import com.awesomelld.rideshare.dto.PaymentResponse;
import com.awesomelld.rideshare.dto.TripRequest;
import com.awesomelld.rideshare.dto.TripResponse;
import com.awesomelld.rideshare.entity.DriverEntity;
import com.awesomelld.rideshare.entity.PaymentEntity;
import com.awesomelld.rideshare.entity.RiderEntity;
import com.awesomelld.rideshare.entity.TripEntity;
import com.awesomelld.rideshare.enums.DriverStatus;
import com.awesomelld.rideshare.enums.PaymentStatus;
import com.awesomelld.rideshare.enums.TripStatus;
import com.awesomelld.rideshare.exception.InvalidStateException;
import com.awesomelld.rideshare.exception.ResourceNotFoundException;
import com.awesomelld.rideshare.mapper.RideShareMapper;
import com.awesomelld.rideshare.repository.DriverRepository;
import com.awesomelld.rideshare.repository.PaymentRepository;
import com.awesomelld.rideshare.repository.RiderRepository;
import com.awesomelld.rideshare.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RideShareService {

    private final RiderRepository riderRepository;
    private final DriverRepository driverRepository;
    private final TripRepository tripRepository;
    private final PaymentRepository paymentRepository;
    private final RideShareMapper mapper;

    @Transactional
    public TripResponse requestTrip(TripRequest request) {
        RiderEntity rider = riderRepository.findById(request.riderId())
                .orElseThrow(() -> new ResourceNotFoundException("Rider not found"));

        TripEntity trip = new TripEntity();
        trip.setRider(rider);
        trip.setPickupLat(request.pickupLat());
        trip.setPickupLng(request.pickupLng());
        trip.setDropLat(request.dropLat());
        trip.setDropLng(request.dropLng());
        trip.setStatus(TripStatus.REQUESTED);
        trip.setFareEstimate(estimateFare(request));
        trip.setRequestedAt(Instant.now());

        assignNearestDriver(trip);

        TripEntity saved = tripRepository.save(trip);
        return mapper.toTripResponse(saved);
    }

    @Transactional
    public TripResponse startTrip(Long tripId) {
        TripEntity trip = getTrip(tripId);
        if (trip.getStatus() != TripStatus.ACCEPTED) {
            throw new InvalidStateException("Trip must be accepted before starting");
        }
        trip.setStatus(TripStatus.ONGOING);
        trip.setStartedAt(Instant.now());
        return mapper.toTripResponse(tripRepository.save(trip));
    }

    @Transactional
    public TripResponse completeTrip(Long tripId, BigDecimal fareFinal) {
        TripEntity trip = getTrip(tripId);
        if (trip.getStatus() != TripStatus.ONGOING) {
            throw new InvalidStateException("Trip must be ongoing to complete");
        }
        trip.setStatus(TripStatus.COMPLETED);
        trip.setCompletedAt(Instant.now());
        trip.setFareFinal(fareFinal);
        return mapper.toTripResponse(tripRepository.save(trip));
    }

    public TripResponse getTripResponse(Long tripId) {
        TripEntity trip = getTrip(tripId);
        return mapper.toTripResponse(trip);
    }

    @Transactional
    public PaymentResponse pay(PaymentRequest request) {
        TripEntity trip = getTrip(request.tripId());
        if (trip.getStatus() != TripStatus.COMPLETED) {
            throw new InvalidStateException("Trip must be completed before payment");
        }
        PaymentEntity payment = new PaymentEntity(trip, request.amount(), request.method());
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setPaidAt(Instant.now());
        PaymentEntity saved = paymentRepository.save(payment);
        return mapper.toPaymentResponse(saved);
    }

    private TripEntity getTrip(Long tripId) {
        return tripRepository.findById(tripId)
                .orElseThrow(() -> new ResourceNotFoundException("Trip not found"));
    }

    private void assignNearestDriver(TripEntity trip) {
        List<DriverEntity> available = driverRepository.findByStatus(DriverStatus.AVAILABLE);
        available.stream()
                .min(Comparator.comparingDouble(d -> distance(d.getLat(), d.getLng(), trip.getPickupLat(), trip.getPickupLng())))
                .ifPresent(driver -> {
                    driver.setStatus(DriverStatus.ON_TRIP);
                    trip.setDriver(driver);
                    trip.setStatus(TripStatus.ACCEPTED);
                });
    }

    private BigDecimal estimateFare(TripRequest request) {
        double distanceKm = distance(request.pickupLat(), request.pickupLng(), request.dropLat(), request.dropLng());
        double base = 5.0;
        double perKm = 12.0;
        return BigDecimal.valueOf(base + perKm * distanceKm).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    private double distance(Double lat1, Double lng1, Double lat2, Double lng2) {
        if (lat1 == null || lng1 == null || lat2 == null || lng2 == null) {
            return Double.MAX_VALUE;
        }
        double dx = lat1 - lat2;
        double dy = lng1 - lng2;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
