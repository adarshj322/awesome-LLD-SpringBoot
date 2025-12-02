package com.awesomelld.vendingmachine.controller;

import com.awesomelld.vendingmachine.dto.AddPaymentRequest;
import com.awesomelld.vendingmachine.dto.DispenseResponse;
import com.awesomelld.vendingmachine.dto.PurchaseSessionResponse;
import com.awesomelld.vendingmachine.dto.RefundResponse;
import com.awesomelld.vendingmachine.dto.StartSessionRequest;
import com.awesomelld.vendingmachine.dto.VendingMachineResponse;
import com.awesomelld.vendingmachine.service.VendingMachineService;
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

import java.util.List;

@RestController
@RequestMapping("/api/vending-machines")
@RequiredArgsConstructor
public class VendingMachineController {

    private final VendingMachineService service;

    @GetMapping
    public List<VendingMachineResponse> listMachines() {
        return service.listMachines();
    }

    @PostMapping("/{code}/sessions")
    @ResponseStatus(HttpStatus.CREATED)
    public PurchaseSessionResponse startSession(@PathVariable String code, @Valid @RequestBody StartSessionRequest request) {
        return service.startSession(code, request);
    }

    @GetMapping("/{code}/sessions/{sessionId}")
    public PurchaseSessionResponse getSession(@PathVariable String code, @PathVariable Long sessionId) {
        return service.getSessionResponse(code, sessionId);
    }

    @PostMapping("/{code}/sessions/{sessionId}/payments")
    public PurchaseSessionResponse addPayment(@PathVariable String code,
                                              @PathVariable Long sessionId,
                                              @Valid @RequestBody AddPaymentRequest request) {
        return service.addPayment(code, sessionId, request);
    }

    @PostMapping("/{code}/sessions/{sessionId}/dispense")
    public DispenseResponse dispense(@PathVariable String code, @PathVariable Long sessionId) {
        return service.dispense(code, sessionId);
    }

    @PostMapping("/{code}/sessions/{sessionId}/refund")
    public RefundResponse refund(@PathVariable String code, @PathVariable Long sessionId) {
        return service.refund(code, sessionId);
    }
}
