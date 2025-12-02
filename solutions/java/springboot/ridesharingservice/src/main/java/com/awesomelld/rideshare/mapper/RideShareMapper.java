package com.awesomelld.rideshare.mapper;

import com.awesomelld.rideshare.dto.DriverResponse;
import com.awesomelld.rideshare.dto.PaymentResponse;
import com.awesomelld.rideshare.dto.RiderResponse;
import com.awesomelld.rideshare.dto.TripResponse;
import com.awesomelld.rideshare.entity.DriverEntity;
import com.awesomelld.rideshare.entity.PaymentEntity;
import com.awesomelld.rideshare.entity.RiderEntity;
import com.awesomelld.rideshare.entity.TripEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RideShareMapper {

    RiderResponse toRiderResponse(RiderEntity entity);

    DriverResponse toDriverResponse(DriverEntity entity);

    @Mapping(target = "riderId", source = "rider.id")
    @Mapping(target = "driverId", source = "driver.id")
    TripResponse toTripResponse(TripEntity entity);

    @Mapping(target = "tripId", source = "trip.id")
    PaymentResponse toPaymentResponse(PaymentEntity entity);
}
