package com.awesomelld.vendingmachine.service;

import com.awesomelld.vendingmachine.dto.AddPaymentRequest;
import com.awesomelld.vendingmachine.dto.DispenseResponse;
import com.awesomelld.vendingmachine.dto.PurchaseItemResponse;
import com.awesomelld.vendingmachine.dto.PurchaseSessionResponse;
import com.awesomelld.vendingmachine.dto.RefundResponse;
import com.awesomelld.vendingmachine.dto.StartSessionRequest;
import com.awesomelld.vendingmachine.dto.VendingMachineResponse;
import com.awesomelld.vendingmachine.entity.PaymentLineEntity;
import com.awesomelld.vendingmachine.entity.PurchaseItemEntity;
import com.awesomelld.vendingmachine.entity.PurchaseSessionEntity;
import com.awesomelld.vendingmachine.entity.SlotEntity;
import com.awesomelld.vendingmachine.entity.VendingMachineEntity;
import com.awesomelld.vendingmachine.enums.MachineStatus;
import com.awesomelld.vendingmachine.enums.PurchaseState;
import com.awesomelld.vendingmachine.exception.InventoryException;
import com.awesomelld.vendingmachine.exception.InvalidPurchaseStateException;
import com.awesomelld.vendingmachine.exception.ResourceNotFoundException;
import com.awesomelld.vendingmachine.mapper.VendingMachineMapper;
import com.awesomelld.vendingmachine.repository.PurchaseSessionRepository;
import com.awesomelld.vendingmachine.repository.SlotRepository;
import com.awesomelld.vendingmachine.repository.VendingMachineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VendingMachineService {

    private final VendingMachineRepository machineRepository;
    private final SlotRepository slotRepository;
    private final PurchaseSessionRepository sessionRepository;
    private final VendingMachineMapper mapper;

    public List<VendingMachineResponse> listMachines() {
        return machineRepository.findAll().stream()
                .map(mapper::toMachineResponse)
                .toList();
    }

    @Transactional
    public PurchaseSessionResponse startSession(String machineCode, StartSessionRequest request) {
        VendingMachineEntity machine = getMachine(machineCode);
        if (machine.getStatus() != MachineStatus.ACTIVE) {
            throw new InventoryException("Machine %s is not active".formatted(machineCode));
        }
        SlotEntity slot = slotRepository.findByIdAndMachine_Code(request.slotId(), machineCode)
                .orElseThrow(() -> new ResourceNotFoundException("Slot %d not found on machine %s".formatted(request.slotId(), machineCode)));

        if (slot.getQuantity() < request.quantity()) {
            throw new InventoryException("Slot %s only has %d items".formatted(slot.getPosition(), slot.getQuantity()));
        }

        PurchaseSessionEntity session = new PurchaseSessionEntity();
        session.setMachine(machine);
        session.setState(PurchaseState.PAYMENT_PENDING);

        PurchaseItemEntity item = new PurchaseItemEntity(slot, request.quantity(), slot.getProduct().getPrice());
        session.addItem(item);
        session.setTotalCost(item.totalPrice());
        session.setAmountPaid(BigDecimal.ZERO);
        session.setChangeDue(BigDecimal.ZERO);

        PurchaseSessionEntity saved = sessionRepository.save(session);
        return mapper.toPurchaseSessionResponse(saved);
    }

    @Transactional
    public PurchaseSessionResponse addPayment(String machineCode, Long sessionId, AddPaymentRequest request) {
        PurchaseSessionEntity session = getSession(machineCode, sessionId);
        ensureNotCompleted(session);

        PaymentLineEntity payment = new PaymentLineEntity(request.method(), request.amount(), request.denomination());
        session.addPayment(payment);
        session.setAmountPaid(session.getAmountPaid().add(request.amount()));

        if (session.getAmountPaid().compareTo(session.getTotalCost()) >= 0) {
            session.setState(PurchaseState.DISPENSING);
            session.setChangeDue(session.getAmountPaid().subtract(session.getTotalCost()));
        } else {
            session.setState(PurchaseState.PAYMENT_PENDING);
            session.setChangeDue(BigDecimal.ZERO);
        }

        PurchaseSessionEntity saved = sessionRepository.save(session);
        return mapper.toPurchaseSessionResponse(saved);
    }

    @Transactional
    public DispenseResponse dispense(String machineCode, Long sessionId) {
        PurchaseSessionEntity session = getSession(machineCode, sessionId);
        if (session.getState() != PurchaseState.DISPENSING && session.getState() != PurchaseState.COMPLETED) {
            throw new InvalidPurchaseStateException("Session is not ready to dispense");
        }
        for (PurchaseItemEntity item : session.getItems()) {
            SlotEntity slot = item.getSlot();
            if (slot.getQuantity() < item.getQuantity()) {
                throw new InventoryException("Slot %s ran out of items".formatted(slot.getPosition()));
            }
            slot.setQuantity(slot.getQuantity() - item.getQuantity());
        }
        session.setCompletedAt(Instant.now());
        session.setState(PurchaseState.COMPLETED);
        sessionRepository.save(session);

        List<PurchaseItemResponse> items = mapper.toPurchaseItemResponses(session.getItems());
        return new DispenseResponse(session.getId(), items, session.getChangeDue());
    }

    @Transactional
    public RefundResponse refund(String machineCode, Long sessionId) {
        PurchaseSessionEntity session = getSession(machineCode, sessionId);
        ensureNotCompleted(session);
        session.setState(PurchaseState.REFUNDED);
        session.setCompletedAt(Instant.now());
        session.setChangeDue(session.getAmountPaid());
        sessionRepository.save(session);
        return new RefundResponse(session.getId(), session.getState(), session.getAmountPaid());
    }

    public PurchaseSessionResponse getSessionResponse(String machineCode, Long sessionId) {
        PurchaseSessionEntity session = getSession(machineCode, sessionId);
        return mapper.toPurchaseSessionResponse(session);
    }

    private void ensureNotCompleted(PurchaseSessionEntity session) {
        if (session.getState() == PurchaseState.COMPLETED || session.getState() == PurchaseState.REFUNDED) {
            throw new InvalidPurchaseStateException("Session already finished");
        }
    }

    private VendingMachineEntity getMachine(String code) {
        return machineRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Machine %s not found".formatted(code)));
    }

    private PurchaseSessionEntity getSession(String machineCode, Long sessionId) {
        return sessionRepository.findByIdAndMachine_Code(sessionId, machineCode)
                .orElseThrow(() -> new ResourceNotFoundException("Session %d not found for machine %s".formatted(sessionId, machineCode)));
    }
}
