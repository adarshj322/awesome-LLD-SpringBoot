package com.awesomelld.vendingmachine.dto;

import com.awesomelld.vendingmachine.enums.MachineStatus;

import java.util.List;

public record VendingMachineResponse(
        Long id,
        String code,
        String location,
        MachineStatus status,
        List<SlotResponse> slots
) {
}
