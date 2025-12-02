package com.awesomelld.rideshare.mapper;

import com.awesomelld.rideshare.dto.DriverResponse;
import com.awesomelld.rideshare.dto.PaymentResponse;
import com.awesomelld.rideshare.dto.RiderResponse;
import com.awesomelld.rideshare.dto.TripResponse;
import com.awesomelld.rideshare.entity.DriverEntity;
import com.awesomelld.rideshare.entity.PaymentEntity;
import com.awesomelld.rideshare.entity.RiderEntity;
import com.awesomelld.rideshare.entity.TripEntity;
import com.awesomelld.rideshare.enums.DriverStatus;
import com.awesomelld.rideshare.enums.PaymentMethod;
import com.awesomelld.rideshare.enums.PaymentStatus;
import com.awesomelld.rideshare.enums.TripStatus;
import java.math.BigDecimal;
import java.time.Instant;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-02T18:36:01+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.7 (Azul Systems, Inc.)"
)
@Component
public class RideShareMapperImpl implements RideShareMapper {

    @Override
    public RiderResponse toRiderResponse(RiderEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String phone = null;

        id = entity.getId();
        name = entity.getName();
        phone = entity.getPhone();

        RiderResponse riderResponse = new RiderResponse( id, name, phone );

        return riderResponse;
    }

    @Override
    public DriverResponse toDriverResponse(DriverEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String phone = null;
        DriverStatus status = null;
        BigDecimal rating = null;
        Double lat = null;
        Double lng = null;

        id = entity.getId();
        name = entity.getName();
        phone = entity.getPhone();
        status = entity.getStatus();
        rating = entity.getRating();
        lat = entity.getLat();
        lng = entity.getLng();

        DriverResponse driverResponse = new DriverResponse( id, name, phone, status, rating, lat, lng );

        return driverResponse;
    }

    @Override
    public TripResponse toTripResponse(TripEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Long riderId = null;
        Long driverId = null;
        Long id = null;
        TripStatus status = null;
        Double pickupLat = null;
        Double pickupLng = null;
        Double dropLat = null;
        Double dropLng = null;
        BigDecimal fareEstimate = null;
        BigDecimal fareFinal = null;
        Instant requestedAt = null;
        Instant startedAt = null;
        Instant completedAt = null;

        riderId = entityRiderId( entity );
        driverId = entityDriverId( entity );
        id = entity.getId();
        status = entity.getStatus();
        pickupLat = entity.getPickupLat();
        pickupLng = entity.getPickupLng();
        dropLat = entity.getDropLat();
        dropLng = entity.getDropLng();
        fareEstimate = entity.getFareEstimate();
        fareFinal = entity.getFareFinal();
        requestedAt = entity.getRequestedAt();
        startedAt = entity.getStartedAt();
        completedAt = entity.getCompletedAt();

        TripResponse tripResponse = new TripResponse( id, status, riderId, driverId, pickupLat, pickupLng, dropLat, dropLng, fareEstimate, fareFinal, requestedAt, startedAt, completedAt );

        return tripResponse;
    }

    @Override
    public PaymentResponse toPaymentResponse(PaymentEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Long tripId = null;
        Long id = null;
        PaymentMethod method = null;
        PaymentStatus status = null;
        BigDecimal amount = null;
        Instant paidAt = null;

        tripId = entityTripId( entity );
        id = entity.getId();
        method = entity.getMethod();
        status = entity.getStatus();
        amount = entity.getAmount();
        paidAt = entity.getPaidAt();

        PaymentResponse paymentResponse = new PaymentResponse( id, tripId, method, status, amount, paidAt );

        return paymentResponse;
    }

    private Long entityRiderId(TripEntity tripEntity) {
        if ( tripEntity == null ) {
            return null;
        }
        RiderEntity rider = tripEntity.getRider();
        if ( rider == null ) {
            return null;
        }
        Long id = rider.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long entityDriverId(TripEntity tripEntity) {
        if ( tripEntity == null ) {
            return null;
        }
        DriverEntity driver = tripEntity.getDriver();
        if ( driver == null ) {
            return null;
        }
        Long id = driver.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long entityTripId(PaymentEntity paymentEntity) {
        if ( paymentEntity == null ) {
            return null;
        }
        TripEntity trip = paymentEntity.getTrip();
        if ( trip == null ) {
            return null;
        }
        Long id = trip.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
