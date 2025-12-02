package com.awesomelld.rideshare.service;

import com.awesomelld.rideshare.dto.PaymentRequest;
import com.awesomelld.rideshare.dto.PaymentResponse;
import com.awesomelld.rideshare.dto.TripRequest;
import com.awesomelld.rideshare.dto.TripResponse;
import com.awesomelld.rideshare.enums.PaymentMethod;
import com.awesomelld.rideshare.enums.TripStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class RideShareServiceTest {

    @Autowired
    private RideShareService service;

    @Test
    void endToEndTrip() {
        TripRequest request = new TripRequest(1L, 12.97, 77.59, 12.95, 77.62);
        TripResponse trip = service.requestTrip(request);
        assertThat(trip.status()).isIn(TripStatus.REQUESTED, TripStatus.ACCEPTED);

        TripResponse started = service.startTrip(trip.id());
        assertThat(started.status()).isEqualTo(TripStatus.ONGOING);

        TripResponse completed = service.completeTrip(trip.id(), new BigDecimal("70.00"));
        assertThat(completed.status()).isEqualTo(TripStatus.COMPLETED);

        PaymentResponse payment = service.pay(new PaymentRequest(trip.id(), new BigDecimal("70.00"), PaymentMethod.CASH));
        assertThat(payment.status().name()).isEqualTo("SUCCESS");
    }
}
