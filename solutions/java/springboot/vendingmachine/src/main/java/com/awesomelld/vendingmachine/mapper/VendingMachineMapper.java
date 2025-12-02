package com.awesomelld.vendingmachine.mapper;

import com.awesomelld.vendingmachine.dto.PaymentLineResponse;
import com.awesomelld.vendingmachine.dto.ProductSummary;
import com.awesomelld.vendingmachine.dto.PurchaseItemResponse;
import com.awesomelld.vendingmachine.dto.PurchaseSessionResponse;
import com.awesomelld.vendingmachine.dto.SlotResponse;
import com.awesomelld.vendingmachine.dto.VendingMachineResponse;
import com.awesomelld.vendingmachine.entity.PaymentLineEntity;
import com.awesomelld.vendingmachine.entity.ProductEntity;
import com.awesomelld.vendingmachine.entity.PurchaseItemEntity;
import com.awesomelld.vendingmachine.entity.PurchaseSessionEntity;
import com.awesomelld.vendingmachine.entity.SlotEntity;
import com.awesomelld.vendingmachine.entity.VendingMachineEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VendingMachineMapper {

    @Mapping(target = "product", source = "product")
    SlotResponse toSlotResponse(SlotEntity entity);

    ProductSummary toProductSummary(ProductEntity entity);

    @Mapping(target = "slots", source = "slots")
    VendingMachineResponse toMachineResponse(VendingMachineEntity entity);

    @Mapping(target = "slotId", source = "slot.id")
    @Mapping(target = "slotPosition", source = "slot.position")
    @Mapping(target = "productName", source = "slot.product.name")
    @Mapping(target = "totalPrice", expression = "java(entity.totalPrice())")
    PurchaseItemResponse toPurchaseItemResponse(PurchaseItemEntity entity);

    PaymentLineResponse toPaymentLineResponse(PaymentLineEntity entity);

    @Mapping(target = "sessionId", source = "id")
    @Mapping(target = "machineCode", source = "machine.code")
    @Mapping(target = "items", source = "items")
    @Mapping(target = "payments", source = "payments")
    PurchaseSessionResponse toPurchaseSessionResponse(PurchaseSessionEntity entity);

    List<SlotResponse> toSlotResponses(List<SlotEntity> slots);

    List<PurchaseItemResponse> toPurchaseItemResponses(List<PurchaseItemEntity> items);

    List<PaymentLineResponse> toPaymentResponses(List<PaymentLineEntity> payments);
}
