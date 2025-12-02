package com.awesomelld.vendingmachine.service;

import com.awesomelld.vendingmachine.dto.AddPaymentRequest;
import com.awesomelld.vendingmachine.dto.DispenseResponse;
import com.awesomelld.vendingmachine.dto.PurchaseSessionResponse;
import com.awesomelld.vendingmachine.dto.RefundResponse;
import com.awesomelld.vendingmachine.dto.StartSessionRequest;
import com.awesomelld.vendingmachine.enums.PaymentMethod;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class VendingMachineServiceTest {

    private static final String MACHINE = "AIRPORT_T1";

    @Autowired
    private VendingMachineService service;

    @Test
    void happyPathDispense() {
        PurchaseSessionResponse session = service.startSession(MACHINE, new StartSessionRequest(1L, 2));
        assertThat(session.totalCost()).isEqualByComparingTo("3.00");

        PurchaseSessionResponse afterPayment = service.addPayment(MACHINE, session.sessionId(),
                new AddPaymentRequest(PaymentMethod.COIN, new BigDecimal("3.00"), "3.00"));
        assertThat(afterPayment.amountPaid()).isEqualByComparingTo("3.00");
        assertThat(afterPayment.changeDue()).isEqualByComparingTo("0.00");

        DispenseResponse dispense = service.dispense(MACHINE, session.sessionId());
        assertThat(dispense.dispensedItems()).hasSize(1);
        assertThat(dispense.changeReturned()).isEqualByComparingTo("0.00");
    }

    @Test
    void refundBeforeDispense() {
        PurchaseSessionResponse session = service.startSession(MACHINE, new StartSessionRequest(2L, 1));
        service.addPayment(MACHINE, session.sessionId(),
                new AddPaymentRequest(PaymentMethod.NOTE, new BigDecimal("2.00"), "2.00"));
        RefundResponse refund = service.refund(MACHINE, session.sessionId());
        assertThat(refund.amountRefunded()).isEqualByComparingTo("2.00");
    }
}
