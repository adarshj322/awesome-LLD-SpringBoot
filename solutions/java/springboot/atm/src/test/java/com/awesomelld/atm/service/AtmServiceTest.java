package com.awesomelld.atm.service;

import com.awesomelld.atm.dto.SessionResponse;
import com.awesomelld.atm.dto.TransactionResponse;
import com.awesomelld.atm.enums.TransactionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class AtmServiceTest {

    @Autowired
    private AtmService service;

    @Test
    void endToEndSession() {
        SessionResponse session = service.startSession("ATM_A1", "4111111111111111", "1234");
        UUID sessionId = session.sessionId();
        assertThat(session.balance()).isGreaterThan(BigDecimal.ZERO);

        TransactionResponse withdrawal = service.withdraw(sessionId, new BigDecimal("100.00"));
        assertThat(withdrawal.type()).isEqualTo(TransactionType.WITHDRAWAL);

        TransactionResponse deposit = service.deposit(sessionId, new BigDecimal("50.00"));
        assertThat(deposit.type()).isEqualTo(TransactionType.DEPOSIT);

        service.endSession(sessionId);
    }
}
