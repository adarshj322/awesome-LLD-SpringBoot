package com.awesomelld.atm.controller;

import com.awesomelld.atm.dto.AuthenticateRequest;
import com.awesomelld.atm.dto.BalanceResponse;
import com.awesomelld.atm.dto.CashRequest;
import com.awesomelld.atm.dto.SessionResponse;
import com.awesomelld.atm.dto.TransactionResponse;
import com.awesomelld.atm.service.AtmService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/atm")
@Validated
@RequiredArgsConstructor
public class AtmController {

    private final AtmService service;

    /**
     * Authenticate a card/PIN on a specific ATM and start a session.
     */
    @PostMapping("/{atmCode}/sessions")
    @ResponseStatus(HttpStatus.CREATED)
    public SessionResponse startSession(@PathVariable String atmCode, @Valid @RequestBody AuthenticateRequest request) {
        return service.startSession(atmCode, request.cardNumber(), request.pin());
    }

    /**
     * Check balance for the active session.
     */
    @PostMapping("/sessions/{sessionId}/balance")
    public BalanceResponse balance(@PathVariable UUID sessionId) {
        return service.balance(sessionId);
    }

    /**
     * Withdraw cash (if account and ATM have sufficient funds).
     */
    @PostMapping("/sessions/{sessionId}/withdraw")
    public TransactionResponse withdraw(@PathVariable UUID sessionId, @Valid @RequestBody CashRequest request) {
        return service.withdraw(sessionId, request.amount());
    }

    /**
     * Deposit cash into the account via the ATM.
     */
    @PostMapping("/sessions/{sessionId}/deposit")
    public TransactionResponse deposit(@PathVariable UUID sessionId, @Valid @RequestBody CashRequest request) {
        return service.deposit(sessionId, request.amount());
    }

    /**
     * End a session explicitly.
     */
    @PostMapping("/sessions/{sessionId}/end")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void endSession(@PathVariable UUID sessionId) {
        service.endSession(sessionId);
    }
}
